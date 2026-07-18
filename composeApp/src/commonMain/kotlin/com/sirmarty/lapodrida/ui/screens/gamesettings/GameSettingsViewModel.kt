package com.sirmarty.lapodrida.ui.screens.gamesettings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sirmarty.lapodrida.domain.entities.Game
import com.sirmarty.lapodrida.domain.entities.Player
import com.sirmarty.lapodrida.domain.repository.GameRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock

class GameSettingsViewModel(private val gameRepository: GameRepository) :
    ViewModel() {

    private val _uiState = MutableStateFlow(GameSettingsScreenState())
    val uiState: StateFlow<GameSettingsScreenState> = _uiState

    fun createGame() {
        val game = Game(
            timestamp = Clock.System.now().epochSeconds,
            settings = _uiState.value.gameSettings,
            players = _uiState.value.playerNames.map { Player(0, it, emptyList()) }
        )

        viewModelScope.launch {
            try {
                gameRepository.createGame(game)
                _uiState.update { state ->
                    state.copy(isGameCreated = true)
                }
            } catch (e: Exception) {
                TODO() // Manage errors
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