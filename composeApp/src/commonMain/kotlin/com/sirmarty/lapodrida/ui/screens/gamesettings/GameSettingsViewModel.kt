package com.sirmarty.lapodrida.ui.screens.gamesettings

import androidx.lifecycle.ViewModel
import com.sirmarty.lapodrida.domain.models.GameSettingsModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class GameSettingsViewModel: ViewModel() {

    private val _uiState = MutableStateFlow(GameSettingsModel())
    val uiState: StateFlow<GameSettingsModel> = _uiState


    fun updateNumberOfPlayers(numberOfPlayers: Int) {
        _uiState.update { state -> state.copy(numberOfPlayers = numberOfPlayers) }
    }

    fun updateIsIndianRound(isIndianRound: Boolean) {
        _uiState.update { state -> state.copy(isIndianRound = isIndianRound) }
    }

    fun updatePointsPerWin(pointsPerWin: Int) {
        _uiState.update { state -> state.copy(pointsPerWin = pointsPerWin) }
    }

    fun updatePointsPerHand(pointsPerHand: Int) {
        _uiState.update { state -> state.copy(pointsPerHand = pointsPerHand) }
    }

}