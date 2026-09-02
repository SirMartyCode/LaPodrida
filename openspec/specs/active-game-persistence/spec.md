# active-game-persistence Specification

## Purpose
Stores the single game currently in progress as durable, observable state, so the rest of the app can read and react to its current round, players, and predictions.

## Requirements

### Requirement: At most one game in progress
The system SHALL treat at most one stored game as "in progress" (not finished) at a time, and SHALL expose that game reactively so observers are notified whenever it changes.

#### Scenario: Observing the in-progress game
- **WHEN** a game is stored as in progress
- **THEN** the system emits that game to observers, and emits again whenever it is updated

#### Scenario: No game in progress
- **WHEN** no game is stored as in progress
- **THEN** the system reports that no game is in progress

### Requirement: Checking for a game in progress without loading it
The system SHALL let callers cheaply check whether a game is in progress without retrieving the game's full data.

#### Scenario: Existence check
- **WHEN** a caller asks whether a game is in progress
- **THEN** the system answers true or false based on whether an unfinished game is stored, without returning the game itself

### Requirement: Deleting the in-progress game
The system SHALL let the in-progress game be permanently deleted on request. Deletion SHALL NOT be reversible.

#### Scenario: Deleting the in-progress game
- **WHEN** the in-progress game is deleted
- **THEN** the system no longer reports any game in progress, and the deleted game's data is not recoverable

### Requirement: Recording predictions against the in-progress game
The system SHALL let a full set of per-player predictions for the in-progress game's current round be applied and persisted in one operation.

#### Scenario: Predictions saved
- **WHEN** a full map of player-to-prediction values for the current round is submitted
- **THEN** the system stores those predictions on the current round of the in-progress game
