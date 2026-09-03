## Context

See proposal.md - Why/What Changes for motivation and scope. Relevant current state:

- `PredictionsViewModel` keeps confirmed predictions in `playerPredictions: MutableStateFlow<Map<Int, Int>>`. The current bidder is derived as `round.predictionOrder[predictions.size]` - this only works because `predictions.size` never changes when an existing key is overwritten (`it.plus(playerId to value)`), so it stays valid for corrections to already-confirmed players without any change.
- `PredictionRulesService.getForbiddenValue` is already a pure function of `pendingPredictions` - it recomputes from scratch every time, so correcting an earlier prediction and re-deriving the current bidder's allowed values needs no new invalidation logic.
- `PredictionsScreen`'s `PredictionsContent` already holds its stepper position in `remember(state.currentPlayerId) { mutableIntStateOf(0) }`, which resets whenever the shown player changes - this is the same lifecycle a per-page pager needs for "unconfirmed edits are discarded."
- `submitPredictions` on `CurrentGameRepository` exists and is implemented, but the call site in `PredictionsViewModel.confirmPrediction` is commented out.

## Goals / Non-Goals

**Goals:**
- Let the user navigate to any already-confirmed player's page in the round, plus the current bidder's page, and correct a value there.
- Guarantee, by construction, that the no-exact-total rule can never be violated retroactively - not by re-validating after the fact, but by making it structurally impossible to reach an already-confirmed "last bidder" page.
- Reuse the existing per-player entry layout unchanged as the pager's page content.
- Wire up the existing but unused `submitPredictions` path once the round is fully predicted.

**Non-Goals:**
- Editing predictions after the round is fully predicted and submitted (reopening a submitted round is a separate, larger feature and is not requested here).
- Canceling out of predictions entry / back-stack changes beyond the existing "goBack once submitted" behavior.
- Persisting in-progress, unconfirmed edits across process death - stays in-memory only, as today.

## Decisions

**Reachable page count = confirmed players + the current bidder, nothing more.** The pager is sized to `predictions.size + 1` pages, mapped over `round.predictionOrder`. This is what makes the "no retroactive invalidation" guarantee structural rather than something that needs re-checking: the true last bidder in the order only ever becomes reachable once every earlier player has confirmed, and the instant that last bidder confirms, `predictions.size` becomes equal to the participant count and the pager has no further pages to show - it locks by simply running out of pages, not by an explicit "isLocked" flag.

**Current bidder index logic is unchanged.** `round.predictionOrder[predictions.size]` continues to identify the active (rightmost, not-yet-confirmed) page after a correction to an earlier page, because corrections overwrite an existing map entry rather than adding one. No restructuring of `playerPredictions` is needed.

**Discard-on-navigate-away rides on Compose's own page disposal, not a hand-rolled dirty flag.** Each page keeps its stepper position in `remember(playerId) { mutableIntStateOf(...) }` seeded from that player's confirmed value (or the first allowed value, for the not-yet-confirmed current page, as today). With `HorizontalPager`'s default of not retaining offscreen pages, navigating away disposes that remembered state; navigating back recomposes it fresh from the confirmed value. This means "discard" requires no explicit state machine - it falls out of not writing anything to `playerPredictions` until "Confirmar" is pressed. Considered keeping a ViewModel-level draft map instead; rejected because it would require explicit invalidation on navigation and duplicate what Compose's composition lifecycle already gives for free.
- To raise the "change discarded" notice, use a `DisposableEffect` scoped to each page that compares the page's final stepper value to the value it was seeded with, and emits a one-shot local notice (e.g. a `SnackbarHostState` message) on dispose if they differ. This is UI-local; it does not need to reach the ViewModel, since nothing was ever persisted there.
- Pin `beyondViewportPageCount = 0` explicitly on the pager rather than relying on the current default, so a later performance tweak to that value doesn't silently break the discard guarantee.

**`PredictionsUiState` describes pages, not just the single active player.** Replace the single-player shape with an ordered list of page descriptors (player id/name, previously confirmed value or null for the active page, that page's allowed values) plus the index of the initially-shown (current bidder's) page. `allowedValues` per page is computed exactly as `PredictionRulesService` does today - only the true last-in-order page ever gets a restricted range; every reopened earlier page always gets the full `0..cardsPerPlayer` range, unchanged from current behavior.

**Wire up `submitPredictions`.** When a confirm brings `predictions.size` to `round.participations.size`, call `currentGameRepository.submitPredictions(predictions)` then `navigator.goBack()`, replacing the commented-out block.

**Cleanup while touching this feature.** Drop the unused `currentRound` val in `PredictionsViewModel`; re-review `toUiState` and `PredictionsContent` for naming/structure once they're reshaped around pages, favoring the existing derive-don't-store style already used for the forbidden-value rule.

## Risks / Trade-offs

- Relying on `HorizontalPager`'s page-disposal lifecycle for discard semantics is implicit → mitigated by pinning `beyondViewportPageCount = 0` explicitly and covering it with a UI test that edits a reopened page, swipes away, and swipes back.
- A pager is a new UI pattern in this codebase (no prior `HorizontalPager` usage) → mitigated by keeping the existing entry layout as-is inside each page, so the only new surface is the page container and navigation, not the input controls themselves.
- Basing "locked" on running out of pages rather than an explicit flag could be less obvious to a future reader than a named state → mitigated by documenting the invariant inline where the page count is computed.
