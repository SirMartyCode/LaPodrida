package com.sirmarty.lapodrida.ui.screens.game

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sirmarty.lapodrida.domain.repository.CurrentGameRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class GameViewModel(
    currentGameRepository: CurrentGameRepository,
    gameUiMapper: GameUiMapper,
) : ViewModel() {
    val uiState: StateFlow<GameUi?> = currentGameRepository.game
        .map { game -> game?.let(gameUiMapper::map) }
        .stateIn(viewModelScope, SharingStarted.Lazily, null)
}
