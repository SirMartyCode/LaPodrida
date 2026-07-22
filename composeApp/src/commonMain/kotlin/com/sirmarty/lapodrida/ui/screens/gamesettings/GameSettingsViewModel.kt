package com.sirmarty.lapodrida.ui.screens.gamesettings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sirmarty.lapodrida.domain.entities.Game
import com.sirmarty.lapodrida.domain.repository.GameRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.uuid.ExperimentalUuidApi

class GameSettingsViewModel(private val gameRepository: GameRepository) :
    ViewModel() {

    private val _uiState = MutableStateFlow(GameSettingsUiState())
    val uiState: StateFlow<GameSettingsUiState> = _uiState

    @OptIn(ExperimentalUuidApi::class)
    fun createGame() {
        val state = _uiState.value
        val game = Game.create(
            playerNames = state.playerNames,
            pointsPerWin = state.pointsPerWin,
            pointsPerHand = state.pointsPerHand,
            indianRound = state.indianRound
        )

        viewModelScope.launch {
            try {
                gameRepository.createGame(game)
                _uiState.update { it.copy(isGameCreated = true) }
            } catch (e: Exception) {
                TODO() // Manage errors
            }
        }
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