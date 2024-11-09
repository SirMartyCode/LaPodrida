package com.sirmarty.lapodrida.ui.screens.menu

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sirmarty.lapodrida.domain.repository.GameRepository
import com.sirmarty.lapodrida.domain.usecase.NewGameUseCase
import com.sirmarty.lapodrida.domain.usecase.NewGameUseCase.NewGameUseCaseResult.CURRENT_GAME_DELETED
import com.sirmarty.lapodrida.domain.usecase.NewGameUseCase.NewGameUseCaseResult.EXISTING_UNFINISHED_GAME
import com.sirmarty.lapodrida.domain.usecase.NewGameUseCase.NewGameUseCaseResult.SUCCESS
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class MenuViewModel(
    private val gameRepository: GameRepository,
    private val newGameUseCase: NewGameUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(MenuScreenState())
    val uiState: StateFlow<MenuScreenState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            val isThereUnfinishedGame = gameRepository.isThereUnfinishedGame()
            val finishedGames = gameRepository.getFinishedGames()
            _uiState.update { state ->
                state.copy(
                    isThereUnfinishedGame = isThereUnfinishedGame,
                    areThereFinishedGames = finishedGames.isNotEmpty()
                )
            }
        }
    }

    fun newGame(delete: Boolean = false, onNewGameAction: () -> Unit) {
        viewModelScope.launch {
            try {
                when (newGameUseCase(delete)) {
                    SUCCESS -> {
                        onNewGameAction()
                    }

                    EXISTING_UNFINISHED_GAME -> {
                        _uiState.update { state ->
                            state.copy(showDeleteGameDialog = true)
                        }
                    }

                    CURRENT_GAME_DELETED -> {
                        _uiState.update { state ->
                            state.copy(showCurrentGameDeletedDialog = true)
                        }
                    }
                }
            } catch (e: Exception) {
                TODO() // Manage errors
            }
        }

    }

    fun hideDeleteGameDialog() {
        _uiState.update { state ->
            state.copy(showDeleteGameDialog = false)
        }
    }

    fun hideCurrentGameDeletedDialog(onNewGameAction: () -> Unit) {
        _uiState.update { state ->
            state.copy(showCurrentGameDeletedDialog = false)
        }
        onNewGameAction()
    }
}