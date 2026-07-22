package com.sirmarty.lapodrida.ui.screens.gamesettings

import androidx.lifecycle.ViewModel
import com.sirmarty.lapodrida.domain.repository.CurrentGameRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class GameSettingsViewModel(
    private val currentGameRepository: CurrentGameRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(GameSettingsUiState())
    val uiState: StateFlow<GameSettingsUiState> = _uiState

    fun createGame() {
        val state = _uiState.value
        currentGameRepository.startNewGame(
            playerNames = state.playerNames,
            pointsPerWin = state.pointsPerWin,
            pointsPerHand = state.pointsPerHand,
            indianRound = state.indianRound,
        )
        _uiState.update { it.copy(isGameCreated = true) }
    }

    fun increaseNumberOfPlayers() {
        _uiState.update { state ->
            state.copy(
                playerNames = state.playerNames.toMutableList().apply { this.add("") }
            )
        }
    }

    fun decreaseNumberOfPlayers() {
        _uiState.update { state ->
            state.copy(
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
            state.copy(indianRound = isIndianRound)
        }
    }

    fun updatePointsPerWin(pointsPerWin: Int) {
        _uiState.update { state ->
            state.copy(pointsPerWin = pointsPerWin)
        }
    }

    fun updatePointsPerHand(pointsPerHand: Int) {
        _uiState.update { state ->
            state.copy(pointsPerHand = pointsPerHand)
        }
    }
}