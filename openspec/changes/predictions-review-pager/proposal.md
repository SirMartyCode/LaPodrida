## Why

Today `PredictionsScreen` only ever shows the current bidder: once a player confirms, there is no way to see or correct an earlier player's prediction. Card games are prone to mis-taps, and the only recovery today is to abandon the round. We want a way to review and, when safe, correct already-entered predictions without reopening a fully-predicted round or risking the "no-exact-total" rule for the last bidder.

## What Changes

- Add pager-style navigation across a round's predictions: swiping/tapping moves between pages for every player who has already confirmed a prediction, plus the current bidder's page. Pages for players who have not yet had their turn are not reachable.
- Navigating (swipe or tap) never confirms or discards a value by itself - only pressing the page's "Confirmar" button commits a prediction. This applies uniformly to the current bidder's page and to any already-confirmed page reopened for correction.
- Reopening an already-confirmed page shows its previously confirmed value pre-filled. Changing the value via the stepper marks the page as having an unconfirmed edit; navigating away from it without confirming discards the edit (reverts to the previously confirmed value) and surfaces a brief notice that the change was discarded.
- Once the round becomes fully predicted (the last bidder confirms), the pager locks: no page remains reachable for further edits, matching today's behavior of proceeding past predictions entry.
- Correcting an earlier player's prediction recomputes the last bidder's forbidden value from the live sum, exactly as it does today for the first pass through bidding order - no new invalidation logic is needed because the rule is already derived at confirm-time rather than stored.
- Keep the existing single-player entry layout (stepper + confirm button) unchanged; the pager wraps that existing layout per page rather than replacing it.
- Clean up `PredictionsViewModel`: remove the unused `currentRound` property and resolve the commented-out `submitPredictions`/navigate-back TODO so a fully-predicted round actually submits, alongside any other readability/reactivity improvements found while touching this feature.

## Capabilities

### New Capabilities
(none)

### Modified Capabilities
- `predictions`: adds requirements for reviewing/correcting already-confirmed predictions via pager navigation, the "navigation never saves" rule, discard-on-navigate-away behavior, and locking the pager once the round is fully predicted.

## Impact

- `composeApp/src/commonMain/kotlin/com/sirmarty/lapodrida/ui/screens/predictions/PredictionsScreen.kt`: wrap existing entry layout in a pager (page content unchanged).
- `composeApp/src/commonMain/kotlin/com/sirmarty/lapodrida/ui/screens/predictions/PredictionsViewModel.kt`: track per-player confirmed predictions plus in-progress (unconfirmed) edits per page; wire up `CurrentGameRepository.submitPredictions` and `navigator.goBack()` once the round is fully predicted; drop unused `currentRound`.
- `composeApp/src/commonMain/kotlin/com/sirmarty/lapodrida/ui/screens/predictions/PredictionsUiState.kt`: extend state to describe reachable pages, the current bidder, and per-page dirty/discard status.
- No changes to `PredictionRulesService.kt` - the existing derived forbidden-value calculation already stays correct under retroactive edits.
- No database/schema changes; predictions remain unsubmitted (in-memory) until the round is fully predicted.
