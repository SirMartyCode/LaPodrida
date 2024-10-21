package com.sirmarty.lapodrida.ui.screens.gamesettings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sirmarty.lapodrida.domain.usecase.SaveGameSettingsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class GameSettingsViewModel(private val saveGameSettingsUseCase: SaveGameSettingsUseCase) :
    ViewModel() {

    private val _uiState = MutableStateFlow(GameSettingsScreenState())
    val uiState: StateFlow<GameSettingsScreenState> = _uiState

    fun save() {
        viewModelScope.launch {
            saveGameSettingsUseCase(_uiState.value.gameSettings)
        }
    }

    fun increaseNumberOfPlayers() {
        _uiState.update { state ->
            val newNumberOfPlayers = state.gameSettings.numberOfPlayers + 1
            state.copy(
                gameSettings = state.gameSettings.copy(numberOfPlayers = newNumberOfPlayers),
                playerNames = state.playerNames.toMutableList().apply { this.add("") }
            )
        }
    }

    fun decreaseNumberOfPlayers() {
        _uiState.update { state ->
            val newNumberOfPlayers = state.gameSettings.numberOfPlayers - 1
            state.copy(
                gameSettings = state.gameSettings.copy(numberOfPlayers = newNumberOfPlayers),
                playerNames = state.playerNames.toMutableList().apply { this.removeLast() }
            )
        }
    }


    fun updatePlayerName(index: Int, name: String) {
        _uiState.update { state ->
            state.copy(
                playerNames = state.playerNames.toMutableList().apply { this[index] = name })
        }
    }

    fun updateIsIndianRound(isIndianRound: Boolean) {
        _uiState.update { state ->
            state.copy(gameSettings = state.gameSettings.copy(isIndianRound = isIndianRound))
        }
    }

    fun updatePointsPerWin(pointsPerWin: Int) {
        _uiState.update { state ->
            state.copy(gameSettings = state.gameSettings.copy(pointsPerWin = pointsPerWin))
        }
    }

    fun updatePointsPerHand(pointsPerHand: Int) {
        _uiState.update { state ->
            state.copy(gameSettings = state.gameSettings.copy(pointsPerHand = pointsPerHand))
        }
    }
}