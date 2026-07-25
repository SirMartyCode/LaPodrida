package com.sirmarty.lapodrida.data.repository

import com.sirmarty.lapodrida.domain.entities.Game
import com.sirmarty.lapodrida.domain.entities.GameSettings
import com.sirmarty.lapodrida.domain.repository.CurrentGameRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class InMemoryCurrentGameRepository : CurrentGameRepository {
    private val _game = MutableStateFlow<Game?>(null)
    override val game: StateFlow<Game?> = _game.asStateFlow()

    override fun startNewGame(settings: GameSettings) {
        val newGame =  Game.create(
            playerNames = settings.playerNames,
            pointsPerWin = settings.pointsPerWin,
            pointsPerHand = settings.pointsPerHand,
            indianRound = settings.indianRound,
        )
        _game.update { newGame }
    }
}
