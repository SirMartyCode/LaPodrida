## Why

`openspec init` was just run on an existing, partially-built codebase, so `openspec/specs/` is empty. Before any future work can be proposed as a delta against "what the system currently does," the currently-implemented behavior needs to be captured as baseline specs. Doing this now, while the recent game/predictions/scoring work is fresh, avoids the specs drifting from reality later.

## What Changes

- Add baseline `spec.md` files for the six capabilities that are actually implemented today: `game-setup`, `predictions`, `active-game-persistence`, `score-table-display`, `menu-navigation`, `localization`.
- Each spec documents only currently-working, verifiable behavior. Flows that exist in code but do not function end-to-end today (advancing to the next round, computing a real per-round score, reaching the game-history screen, and graceful error handling when starting a new game fails) are deliberately **not** asserted as requirements — see `design.md` for the full list. They are left for future change proposals that actually fix/finish them.
- No application code changes.

## Capabilities

### New Capabilities
- `game-setup`: configuring player count/names and game rules before a game starts, and deriving the fixed round sequence from those settings
- `predictions`: the per-round, one-player-at-a-time bidding flow and the rule that the sum of predictions can never equal the round's card count
- `active-game-persistence`: storing and observing the single in-progress game
- `score-table-display`: the scoreboard grid shown during a game
- `menu-navigation`: the home screen's New Game / Continue / History entry points and the confirm-delete flow when starting a new game over an existing one
- `localization`: switching the app's active display language

### Modified Capabilities
(none — this is the first set of specs for the project)

## Impact

Documentation only: adds files under `openspec/specs/`. Read (not modified) source: `composeApp/src/commonMain/kotlin/com/sirmarty/lapodrida/domain/**`, `data/repository/**`, `data/database/**`, `ui/screens/{menu,gamesettings,game,predictions}/**`, `ui/components/ScoreTable.kt`, `di/DomainModule.kt`.
