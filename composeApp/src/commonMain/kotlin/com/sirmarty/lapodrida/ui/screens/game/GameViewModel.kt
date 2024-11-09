package com.sirmarty.lapodrida.ui.screens.game

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sirmarty.lapodrida.domain.entities.Game
import com.sirmarty.lapodrida.domain.repository.GameRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class GameViewModel(private val gameRepository: GameRepository): ViewModel() {

    private val _uiState = MutableStateFlow<Game?>(null)
    val uiState: StateFlow<Game?> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            if (gameRepository.isThereUnfinishedGame()) {
                val result = gameRepository.getCurrentGame()
                _uiState.value = result
            } else {
                // ERROR
            }
        }
    }
}