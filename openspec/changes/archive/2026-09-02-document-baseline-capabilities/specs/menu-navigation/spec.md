## Purpose

Presents the app's home screen and arbitrates what happens when the user asks to start a new game while a game is already in progress.

## ADDED Requirements

### Requirement: Continue and history availability reflect stored state
The system SHALL enable the "Continue" entry point only while a game is in progress, and SHALL enable the "Game History" entry point only while at least one finished game is stored, showing a loading state for each until that check resolves.

#### Scenario: Continue reflects game-in-progress state
- **WHEN** the home screen checks whether a game is in progress
- **THEN** it shows a loading state until the check resolves, then enables "Continue" only if a game is in progress

#### Scenario: History reflects finished-games state
- **WHEN** the home screen checks whether any finished games are stored
- **THEN** it shows a loading state until the check resolves, then enables "Game History" only if at least one finished game exists

### Requirement: Starting a new game with none in progress
When the user chooses "New Game" and no game is in progress, the system SHALL proceed directly to game settings.

#### Scenario: No game in progress
- **WHEN** the user chooses "New Game" and no game is in progress
- **THEN** the system navigates to game settings

### Requirement: Starting a new game with one already in progress
When the user chooses "New Game" while a game is in progress, the system SHALL ask the user to confirm that continuing will permanently delete the in-progress game, rather than starting immediately.

#### Scenario: Confirmation required
- **WHEN** the user chooses "New Game" while a game is in progress
- **THEN** the system shows a confirmation dialog warning that the in-progress game will be permanently deleted

#### Scenario: Confirming deletes the game and proceeds
- **WHEN** the user confirms deletion from that dialog
- **THEN** the system permanently deletes the in-progress game, shows an acknowledgement, and navigates to game settings once that acknowledgement is dismissed

#### Scenario: Canceling leaves the game intact
- **WHEN** the user cancels that dialog
- **THEN** the in-progress game is left unchanged and the system does not navigate away from the home screen

### Requirement: Continue navigates directly to the current game
The system SHALL navigate straight to the game screen when the user chooses "Continue", without re-checking that a game is in progress at that moment.

#### Scenario: Continue navigation
- **WHEN** the user chooses "Continue"
- **THEN** the system navigates to the game screen
