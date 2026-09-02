# score-table-display Specification

## Purpose
Renders the current or finished game as a scrollable scoreboard grid, one row per round and one column per player, showing each round's predictions and, once completed, running totals.

## Requirements

### Requirement: Sticky headers
The scoreboard SHALL keep its round-label column fixed while player columns scroll horizontally, and SHALL keep its player-header row fixed while rounds scroll vertically.

#### Scenario: Scrolling players
- **WHEN** the user scrolls the scoreboard horizontally
- **THEN** the round-label column stays in place while player columns move

#### Scenario: Scrolling rounds
- **WHEN** the user scrolls the scoreboard vertically
- **THEN** the player-header row stays in place while round rows move

### Requirement: Round row labeling
Each round row SHALL show its round number and card count, except that the game's final round, when the indian round setting was enabled for that game, SHALL be labeled as the indian round instead.

#### Scenario: Regular round label
- **WHEN** a row represents a non-final round, or a final round without the indian round enabled
- **THEN** the row shows the round number and its card count

#### Scenario: Indian round label
- **WHEN** a row represents the game's final round and the indian round setting was enabled
- **THEN** the row is labeled as the indian round instead of by round number

### Requirement: Round state classification
The system SHALL classify each round as future, current, or completed by comparing its position to the game's current round position: earlier rounds are completed, the round at the current position is current, and later rounds are future — except that once the game is finished, every round SHALL be classified as completed.

#### Scenario: Classifying an unfinished game's rounds
- **WHEN** a game is not finished and its current round is at position K
- **THEN** rounds before position K are completed, the round at K is current, and rounds after K are future

#### Scenario: Classifying a finished game's rounds
- **WHEN** a game is finished
- **THEN** every round is classified as completed

### Requirement: Distinguishing an unset value from zero
The scoreboard SHALL render a distinct placeholder (not the digit "0") for a prediction or total that has not been recorded yet, and SHALL render the digit "0" only for a recorded value of zero.

#### Scenario: Unset prediction
- **WHEN** a round's participation has no recorded prediction
- **THEN** the cell shows the placeholder, not "0"

#### Scenario: Recorded zero
- **WHEN** a round's participation has a recorded prediction or total of exactly 0
- **THEN** the cell shows "0"

### Requirement: Totals only shown for completed rounds
The system SHALL only display a per-player total score for a round once that round is classified as completed; current and future rounds SHALL show no total regardless of recorded predictions.

#### Scenario: No total before completion
- **WHEN** a round is classified as current or future
- **THEN** the system shows no total for that round, even if predictions are recorded

### Requirement: Prompting for missing predictions
The system SHALL indicate that predictions are pending whenever the game is not finished and at least one participant in the current round has no recorded prediction.

#### Scenario: Pending predictions indicator
- **WHEN** the game is not finished and the current round has at least one participant without a recorded prediction
- **THEN** the system indicates that predictions are pending for the current round
