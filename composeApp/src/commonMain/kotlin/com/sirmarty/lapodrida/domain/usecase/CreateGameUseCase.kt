package com.sirmarty.lapodrida.domain.usecase

import com.sirmarty.lapodrida.domain.entities.Game
import com.sirmarty.lapodrida.domain.entities.GameSettings
import com.sirmarty.lapodrida.domain.entities.Player
import com.sirmarty.lapodrida.domain.repository.GameRepository
import kotlinx.datetime.Clock

class CreateGameUseCase(private val repository: GameRepository) {
    suspend operator fun invoke(gameSettings: GameSettings, playerNames: List<String>) {
        val game = Game(
            timestamp = Clock.System.now().epochSeconds,
            settings = gameSettings,
            players = playerNames.map { Player(0, it, emptyList()) })
        repository.createGame(game)
    }
}