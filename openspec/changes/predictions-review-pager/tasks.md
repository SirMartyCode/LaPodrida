## 1. State shape

- [ ] 1.1 Replace the single-player shape in `PredictionsUiState` with an ordered list of page descriptors (player id, player name, previously confirmed value or null, allowed values) plus the initially-shown page index, and verify it compiles with the existing `PredictionRulesService.getAllowedValues` call feeding each page's allowed values
- [ ] 1.2 Remove the unused `currentRound` property from `PredictionsViewModel` and verify the file still compiles with no unused-value warning

## 2. ViewModel behavior

- [ ] 2.1 Update `PredictionsViewModel.toUiState` to build the page list from `round.predictionOrder` and `playerPredictions`, keeping the current-bidder index derived as `round.predictionOrder[predictions.size]` unchanged, and verify with a unit test that reopening an earlier page after a correction still identifies the correct current bidder
- [ ] 2.2 Verify with a unit test that correcting an earlier player's prediction (overwriting an existing map entry) recomputes the current/last bidder's allowed values from the updated sum, per the "Correcting a prediction recalculates the last bidder's forbidden value" requirement
- [ ] 2.3 Wire up the commented-out submit path: when a confirm brings the predictions map to full size, call `currentGameRepository.submitPredictions(predictions)` then `navigator.goBack()`, and verify with a unit test that submission fires exactly once, only when the round becomes fully predicted

## 3. Pager UI

- [ ] 3.1 Wrap the existing per-player entry layout (stepper + confirm button, unchanged) in a `HorizontalPager` sized to the number of reachable pages (confirmed players + current bidder), with `beyondViewportPageCount = 0` set explicitly, and verify by running the app that swiping reaches every confirmed page and the current page but no further
- [ ] 3.2 Seed each page's stepper position from `remember(playerId) { ... }` using that page's previously confirmed value (or the first allowed value for the current page, as today), and verify by manual test that reopening a confirmed page shows its previously confirmed value
- [ ] 3.3 Add a `DisposableEffect` per page that compares its final stepper value to its seeded value on dispose and shows a discard notice (e.g. a snackbar) when they differ, and verify by manual test: edit a reopened page's value, swipe away without confirming, and see the notice while the previous value is retained
- [ ] 3.4 Verify by manual test that pressing "Confirmar" on a reopened page updates that player's prediction and the page reflects the new confirmed value afterward

## 4. Full-round behavior

- [ ] 4.1 Verify by manual test that once the last remaining player confirms, the pager is left with no further reachable pages and the app navigates back (round submitted), matching the "Pager locks once the round is fully predicted" requirement
- [ ] 4.2 Run the full predictions flow end to end (enter all predictions, correct an earlier one mid-round, confirm through to submission) and verify no regression against `openspec/specs/predictions/spec.md`'s existing bidding-order and no-exact-total requirements
