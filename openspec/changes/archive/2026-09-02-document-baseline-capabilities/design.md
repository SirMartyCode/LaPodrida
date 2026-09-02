## Context

The codebase implements a real domain model and UI for six capabilities (see `proposal.md`), but several pieces of the intended gameplay loop are wired up incompletely or not at all. Writing specs that assert this unfinished behavior as `SHALL` requirements would make the baseline describe intentions rather than reality, which defeats the point of a baseline.

## Goals / Non-Goals

**Goals:**
- Every requirement in the six new spec files reflects behavior that is actually implemented and reachable today.

**Non-Goals:**
- Specifying, fixing, or completing the gaps listed below. They are recorded here so they aren't lost, but are left for future change proposals.

## Decisions

- **Predictions rule engine and entry UI share one capability (`predictions`)** rather than two, since both describe the same user-facing bidding step; only the concrete allowed-value/forbidden-value behavior is specified, not the incomplete submission flow (see gap below).
- **No separate `game-history` or `player-management` capabilities.** Game history has a data layer but no reachable UI entry point (see gap below), so there is no user-facing behavior yet to specify beyond what `menu-navigation`'s "History availability reflects stored state" requirement already covers. Player naming is fully subsumed by `game-setup` and doesn't warrant its own capability.
- **Known gaps are recorded here, not in the spec files**, so the spec files stay purely normative (only behavior that exists and works). Each gap below is a candidate for its own future `openspec propose` change:
  - **Predictions are never actually submitted.** `PredictionsViewModel.confirmPrediction` accumulates predictions in local view-model state only; the call to persist them via `CurrentGameRepository.submitPredictions` and the navigate-back/advance-round logic are commented out. Filling in all players' predictions today does not advance the round.
  - **No round-advancement or game-finish operation exists.** There is no repository method to move to the next round or to mark a game finished (`Game.isFinished` is never set to `true` anywhere in the app).
  - **No real scoring.** `RoundParticipation.score` is hardcoded to `0`; there is no UI or logic to record tricks won or compute a score from predictions vs. tricks won.
  - **Game history is unreachable.** The data layer (`GamesRepository`, `hasFinishedGames`/`getFinishedGames`) and the Menu screen's enabled/disabled state for "Game History" are wired up, but its `onClick` is a no-op and no history/detail screen exists — and since no game is ever marked finished, the query would always return empty anyway.
  - **`MenuViewModel.newGame()` has a guaranteed crash path.** Its `catch` block calls `TODO()`, so any exception during new-game resolution crashes the app rather than being handled.
  - **Localization is inconsistent.** Language switching itself works, but large parts of the UI (most of `GameScreen`/`GameUiMapper`'s text) are hardcoded in Catalan rather than routed through `stringResource`, so switching language does not translate those screens.

## Risks / Trade-offs

- **Baseline goes stale if these gaps are closed without updating specs.** Mitigation: the first change proposal that fixes any gap listed above should add/modify the relevant capability's requirements in the same change.
- **Omitting gaps from the specs means they aren't tracked by `openspec validate`/`openspec list`.** Mitigation: this design doc is preserved under `openspec/changes/archive/` after archiving and remains the canonical list until each gap is turned into its own change.
