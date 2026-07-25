package com.sirmarty.lapodrida.domain.usecase

import com.sirmarty.lapodrida.domain.repository.GamesRepository

class NewGameUseCase(private val gamesRepository: GamesRepository) {
    enum class NewGameUseCaseResult {
        SUCCESS,
        EXISTING_UNFINISHED_GAME,
        CURRENT_GAME_DELETED
    }

    suspend operator fun invoke(delete: Boolean = false): NewGameUseCaseResult  {
        if (gamesRepository.hasGameInProgress()) {
            if (delete) {
                gamesRepository.deleteGameInProgress()
                return NewGameUseCaseResult.CURRENT_GAME_DELETED
            } else
                return NewGameUseCaseResult.EXISTING_UNFINISHED_GAME
        } else {
            return NewGameUseCaseResult.SUCCESS
        }
    }
}
