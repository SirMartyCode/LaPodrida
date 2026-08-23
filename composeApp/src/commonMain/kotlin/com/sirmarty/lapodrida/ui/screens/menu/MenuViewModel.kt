package com.sirmarty.lapodrida.ui.screens.menu

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sirmarty.lapodrida.domain.entities.Language
import com.sirmarty.lapodrida.domain.repository.CurrentGameRepository
import com.sirmarty.lapodrida.domain.repository.GamesRepository
import com.sirmarty.lapodrida.domain.repository.LanguageRepository
import com.sirmarty.lapodrida.domain.usecase.NewGameUseCase
import com.sirmarty.lapodrida.domain.usecase.NewGameUseCase.NewGameUseCaseResult.CURRENT_GAME_DELETED
import com.sirmarty.lapodrida.domain.usecase.NewGameUseCase.NewGameUseCaseResult.EXISTING_UNFINISHED_GAME
import com.sirmarty.lapodrida.domain.usecase.NewGameUseCase.NewGameUseCaseResult.SUCCESS
import com.sirmarty.lapodrida.ui.navigation.Navigator
import com.sirmarty.lapodrida.ui.navigation.Route
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class MenuViewModel(
    private val navigator: Navigator,
    private val languageRepository: LanguageRepository,
    private val currentGameRepository: CurrentGameRepository,
    private val gamesRepository: GamesRepository,
    private val newGameUseCase: NewGameUseCase,
) : ViewModel() {

    private val dialog = MutableStateFlow<MenuDialog?>(null)

    val uiState: StateFlow<MenuUiState> = combine(dialog) { (currentDialog) ->
        MenuUiState(
            enableContinueButton = currentGameRepository.hasGameInProgress(),
            enableHistoryButton = gamesRepository.hasFinishedGames(),
            dialog = currentDialog,
            hideDialog = ::hideDialog,
        )
    }.stateIn(
        viewModelScope, SharingStarted.Lazily, MenuUiState(hideDialog = ::hideDialog)
    )

    fun changeLanguage(language: Language) {
        viewModelScope.launch {
            languageRepository.changeLanguage(language)
        }
    }

    fun newGame(delete: Boolean = false) {
        viewModelScope.launch {
            try {
                when (newGameUseCase(delete)) {
                    SUCCESS -> {
                        navigator.navigateTo(Route.GameSettings)
                    }

                    EXISTING_UNFINISHED_GAME -> {
                        dialog.update { MenuDialog.ConfirmDeleteSavedGame }
                    }

                    CURRENT_GAME_DELETED -> {
                        dialog.update { MenuDialog.SavedGameDeleted }
                    }
                }
            } catch (_: Exception) {
                TODO() // Manage errors
            }
        }
    }

    fun continueGame() = navigator.navigateTo(Route.Game)

    private fun hideDialog() = dialog.update { null }
}
