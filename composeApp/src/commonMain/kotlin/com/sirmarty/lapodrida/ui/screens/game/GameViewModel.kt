package com.sirmarty.lapodrida.ui.screens.game

import androidx.lifecycle.ViewModel
import com.sirmarty.lapodrida.domain.entities.Game
import com.sirmarty.lapodrida.domain.repository.CurrentGameRepository
import kotlinx.coroutines.flow.StateFlow

class GameViewModel(
    currentGameRepository: CurrentGameRepository,
) : ViewModel() {
    val uiState: StateFlow<Game?> = currentGameRepository.game
}