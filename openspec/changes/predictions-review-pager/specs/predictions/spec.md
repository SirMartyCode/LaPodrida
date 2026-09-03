## ADDED Requirements

### Requirement: Reachable prediction pages
The system SHALL make navigable exactly the pages for players who have already confirmed a prediction in the round, plus the current bidder's page. Pages for players whose turn has not yet come SHALL NOT be reachable by navigation.

#### Scenario: Confirmed pages and the current page are reachable
- **WHEN** K players, in bidding order, have already confirmed a prediction in a round
- **THEN** the user can navigate to any of those K pages and to the current bidder's page

#### Scenario: Pages for players who have not yet bid are unreachable
- **WHEN** a player has not yet had their turn to predict in the round
- **THEN** the system does not allow navigation to that player's page

### Requirement: Navigation never commits a value
Navigating between pages, by swipe or by tap, SHALL NOT by itself confirm, change, or discard any player's prediction. Only pressing a page's confirm control SHALL record a prediction.

#### Scenario: Swiping away from the current bidder's page does not confirm it
- **WHEN** the user has selected a value on the current bidder's page but navigates away without pressing the confirm control
- **THEN** no prediction is recorded for that bidder

### Requirement: Reopening a confirmed prediction for correction
WHEN the user navigates to an already-confirmed player's page, the system SHALL pre-fill the entry control with that player's previously confirmed value, allowing the user to change it and press the confirm control to correct it.

#### Scenario: Reopened page shows the previously confirmed value
- **WHEN** the user navigates to a player's page whose prediction was already confirmed
- **THEN** the entry control starts at that previously confirmed value

#### Scenario: Pressing confirm on a reopened page updates the prediction
- **WHEN** the user changes the value on an already-confirmed page and presses the confirm control
- **THEN** that player's prediction is updated to the new value

### Requirement: Unconfirmed edits are discarded on navigation
WHEN the user changes the value on an already-confirmed page and then navigates away without pressing the confirm control, the system SHALL discard the change, retain the previously confirmed value, and SHALL notify the user that the change was discarded.

#### Scenario: Navigating away from an edited but unconfirmed page discards the edit
- **WHEN** the user changes the value on an already-confirmed page and navigates to another page without pressing confirm
- **THEN** the page's prediction remains the previously confirmed value and the system shows a notice that the change was discarded

### Requirement: Correcting a prediction recalculates the last bidder's forbidden value
WHEN an earlier player's prediction is corrected before the round is fully predicted, the system SHALL recompute the current bidder's allowed values, including the last bidder's forbidden value, from the updated sum of predictions, per the existing no-exact-total rule.

#### Scenario: Correcting an earlier prediction updates the last bidder's forbidden value
- **WHEN** an earlier player's confirmed prediction is corrected while one player still remains to predict
- **THEN** the system recomputes that remaining player's forbidden value from the corrected sum before they confirm

### Requirement: Pager locks once the round is fully predicted
WHEN every player in the round has a confirmed prediction, the system SHALL make no page reachable for further edits and SHALL submit the round's predictions.

#### Scenario: Fully predicted round is no longer editable
- **WHEN** the last remaining player confirms their prediction
- **THEN** no page remains reachable for further edits and the round's predictions are submitted
