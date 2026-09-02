## Purpose

Lets each player, in turn, predict how many of the round's tricks they will take, while preventing the sum of every player's prediction in a round from exactly equaling the number of cards dealt that round.

## ADDED Requirements

### Requirement: Bidding order
For a round, the system SHALL determine the order in which players predict by starting at that round's first-to-bid player index and continuing through the game's player list in seating order, wrapping around to the start.

#### Scenario: Bidding order wraps around
- **WHEN** a round's first-to-bid index is P among N seated players
- **THEN** the bidding order is players P, P+1, ..., N-1, 0, ..., P-1

### Requirement: Current bidder
The system SHALL treat the next player in bidding order who has not yet predicted for the round as the current bidder.

#### Scenario: Current bidder advances
- **WHEN** K players, in bidding order, have predicted so far in a round
- **THEN** the current bidder is the (K+1)-th player in that round's bidding order

### Requirement: No-exact-total rule for the last bidder
When exactly one player remains to predict in a round, the system SHALL exclude, from that player's allowed predictions, the single value (if any) that would make the sum of all predictions in the round equal the round's card count. Every other bidder in the round SHALL be allowed to choose any value from 0 to the round's card count.

#### Scenario: Forbidden value excluded for the last bidder
- **WHEN** it is the last remaining player's turn to predict, and one value V between 0 and the round's card count would make the total of all predictions equal the card count
- **THEN** the system excludes V from that player's allowed predictions and allows every other value from 0 to the card count

#### Scenario: No forbidden value when it falls outside the valid range
- **WHEN** it is the last remaining player's turn to predict, and the value that would complete the total to the card count falls outside 0 to the card count
- **THEN** the system allows that player any value from 0 to the card count

#### Scenario: Earlier bidders are unrestricted
- **WHEN** more than one player still has to predict in a round
- **THEN** the system allows the current bidder any value from 0 to the round's card count

### Requirement: Prediction entry offers only allowed values
The prediction entry control SHALL offer only the values currently allowed for the current bidder, in ascending order, skipping any forbidden value entirely rather than showing it disabled.

#### Scenario: Stepping through allowed values
- **WHEN** the current bidder uses the prediction stepper
- **THEN** each step moves to the next or previous value in the allowed-values list, never landing on a forbidden value
