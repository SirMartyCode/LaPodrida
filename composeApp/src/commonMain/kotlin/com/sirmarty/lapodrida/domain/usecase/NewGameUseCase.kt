package com.sirmarty.lapodrida.domain.usecase

import com.sirmarty.lapodrida.domain.repository.GameRepository

class NewGameUseCase(private val gameRepository: GameRepository) {
    enum class NewGameUseCaseResult {
        SUCCESS,
        EXISTING_UNFINISHED_GAME,
        CURRENT_GAME_DELETED
    }

    suspend operator fun invoke(delete: Boolean = false): NewGameUseCaseResult {
        if (gameRepository.isThereUnfinishedGame()) {
            if (delete) {
                gameRepository.deleteCurrentGame()
                return NewGameUseCaseResult.CURRENT_GAME_DELETED
            } else
                return NewGameUseCaseResult.EXISTING_UNFINISHED_GAME
        } else {
            return NewGameUseCaseResult.SUCCESS
        }
    }

}