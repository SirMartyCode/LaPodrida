package com.sirmarty.lapodrida.ui.screens.gamesettings

import androidx.lifecycle.ViewModel
import com.sirmarty.lapodrida.domain.repository.CurrentGameRepository
import com.sirmarty.lapodrida.ui.screens.gamesettings.model.GameSettingsUpdateStrategy
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class GameSettingsViewModel(
    private val currentGameRepository: CurrentGameRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(GameSettingsUiState(updateSettings = ::updateSettings))
    val uiState: StateFlow<GameSettingsUiState> = _uiState

    fun createGame() {
        val state = _uiState.value
        currentGameRepository.startNewGame(settings = state.settings)
        _uiState.update { it.copy(isGameCreated = true) }
    }

    fun updateSettings(strategy: GameSettingsUpdateStrategy) {
        _uiState.update { it.copy(settings = strategy.apply(it.settings)) }
    }
}