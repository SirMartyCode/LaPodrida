package com.sirmarty.lapodrida.ui.screens.gamesettings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sirmarty.lapodrida.domain.usecase.CreateGameUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class GameSettingsViewModel(private val createGameUseCase: CreateGameUseCase) :
    ViewModel() {

    private val _uiState = MutableStateFlow(GameSettingsScreenState())
    val uiState: StateFlow<GameSettingsScreenState> = _uiState

    fun createGame() {
        viewModelScope.launch {
            try {
                createGameUseCase(_uiState.value.gameSettings, _uiState.value.playerNames)
                _uiState.update { state ->
                    state.copy(isGameCreated = true)
                }
            } catch (e: Exception) {
                println(e.message)
            }
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