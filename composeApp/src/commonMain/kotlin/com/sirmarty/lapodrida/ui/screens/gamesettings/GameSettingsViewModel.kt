package com.sirmarty.lapodrida.ui.screens.gamesettings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sirmarty.lapodrida.domain.repository.CurrentGameRepository
import com.sirmarty.lapodrida.ui.navigation.Navigator
import com.sirmarty.lapodrida.ui.navigation.Route
import com.sirmarty.lapodrida.ui.screens.gamesettings.model.GameSettingsUpdateStrategy
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class GameSettingsViewModel(
    private val navigator: Navigator,
    private val currentGameRepository: CurrentGameRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(GameSettingsUiState(updateSettings = ::updateSettings))
    val uiState: StateFlow<GameSettingsUiState> = _uiState

    fun createGame() {
        viewModelScope.launch {
            val state = _uiState.value
            currentGameRepository.startNewGame(settings = state.settings)
            navigator.navigateTo(Route.Game)
        }
    }

    fun updateSettings(strategy: GameSettingsUpdateStrategy) {
        _uiState.update { it.copy(settings = strategy.apply(it.settings)) }
    }
}