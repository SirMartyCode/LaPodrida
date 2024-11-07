package com.sirmarty.lapodrida.domain.usecase

import com.sirmarty.lapodrida.domain.entities.Game
import com.sirmarty.lapodrida.domain.repository.GameRepository

class GetCurrentGameUseCase(private val repository: GameRepository) {
    suspend operator fun invoke(): Game? {
        return repository.getCurrentGame()
    }
}