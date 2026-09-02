## 1. Verify specs against source

- [x] 1.1 Cross-check `specs/game-setup/spec.md` against `GameSettings.kt`, `Game.kt` (`Game.create`), and `GameSettingsScreen.kt`/`GameSettingsViewModel.kt`
- [x] 1.2 Cross-check `specs/predictions/spec.md` against `PredictionRulesService.kt` and `Round.kt` (`predictionOrder`, `isFullyPredicted`)
- [x] 1.3 Cross-check `specs/active-game-persistence/spec.md` against `CurrentGameRepository.kt` and `HybridCurrentGameRepository.kt`
- [x] 1.4 Cross-check `specs/score-table-display/spec.md` against `GameUiMapper.kt`, `GameUi.kt`, and `ScoreTable.kt`
- [x] 1.5 Cross-check `specs/menu-navigation/spec.md` against `MenuViewModel.kt`, `MenuUiState.kt`, and `NewGameUseCase.kt`
- [x] 1.6 Cross-check `specs/localization/spec.md` against `Language.kt` and `MenuScreen.kt`'s language picker

## 2. Validate and archive

- [x] 2.1 Run `openspec validate document-baseline-capabilities --strict` and confirm it passes
- [x] 2.2 Run `openspec archive document-baseline-capabilities` to move these specs into `openspec/specs/`
