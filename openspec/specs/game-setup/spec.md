# game-setup Specification

## Purpose
Lets a player configure the number of participants, their names, and the round rules for a new game before it starts, then derives the fixed sequence of rounds that will drive that game from those settings.

## Requirements

### Requirement: Default game settings
When there is no game in progress, the system SHALL initialize a new game's configuration with 4 players (blank names), the indian round enabled, 10 points per win, and 3 points per hand.

#### Scenario: Fresh game settings
- **WHEN** the user opens game settings with no game in progress
- **THEN** the system shows 4 blank player name fields, the indian round toggle on, points per win at 10, and points per hand at 3

### Requirement: Player count bounds
The system SHALL constrain the number of players to between 2 and 12 inclusive. Increasing the count SHALL append one blank player entry; decreasing the count SHALL remove the last entry in the list, regardless of which entry the user last edited.

#### Scenario: Cannot increase past the maximum
- **WHEN** the player count is 12
- **THEN** the system disables further increases

#### Scenario: Cannot decrease past the minimum
- **WHEN** the player count is 2
- **THEN** the system disables further decreases

#### Scenario: Removing a player removes the last entry
- **WHEN** the user decreases the player count
- **THEN** the system removes the last player entry in the list

### Requirement: Points per win and per hand bounds
The system SHALL constrain "points per win" to between 5 and 30 inclusive, and "points per hand" to between 1 and 10 inclusive.

#### Scenario: Points per win at its bounds
- **WHEN** points per win is at 5 or at 30
- **THEN** the system disables the decrease control at 5 and the increase control at 30

#### Scenario: Points per hand at its bounds
- **WHEN** points per hand is at 1 or at 10
- **THEN** the system disables the decrease control at 1 and the increase control at 10

### Requirement: Round sequence generation
When a game is created for N players, the system SHALL compute `maxRounds = 48 / N` (integer division) and generate one round per value in the sequence `1, 2, ..., maxRounds, maxRounds-1, ..., 1`, each round's card count equal to that value. If the indian round setting is enabled, the system SHALL append one additional final round with a card count of 1, marked as the indian round.

#### Scenario: Round sequence for a 4-player game
- **WHEN** a game is created for 4 players with the indian round disabled
- **THEN** the system generates rounds with card counts 1,2,...,12,11,...,1 (maxRounds=12) and no extra round

#### Scenario: Indian round appended
- **WHEN** a game is created with the indian round enabled
- **THEN** the system appends one extra round after the generated sequence, with a card count of 1, marked as the indian round

### Requirement: First bidder rotates each round
For each round, the system SHALL set that round's first-to-bid player index to the round's position in the sequence (0-based) modulo the number of players.

#### Scenario: First bidder rotates
- **WHEN** a game has N players
- **THEN** the first-to-bid player index for the round at sequence position K is K mod N

### Requirement: Starting a game replaces any current game
The system SHALL, when the user starts a game from the configured settings, create a new game from those settings and store it as the sole in-progress game, replacing any game currently in progress.

#### Scenario: Starting a game
- **WHEN** the user confirms game settings and starts the game
- **THEN** the system creates a game from those settings, stores it as the in-progress game, and navigates to the game screen
