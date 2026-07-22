package com.sirmarty.lapodrida.data.repository

import com.sirmarty.lapodrida.domain.entities.Game
import com.sirmarty.lapodrida.domain.repository.CurrentGameRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class InMemoryCurrentGameRepository : CurrentGameRepository {
    private val _game = MutableStateFlow<Game?>(null)
    override val game: StateFlow<Game?> = _game.asStateFlow()

    override fun startNewGame(
        playerNames: List<String>,
        pointsPerWin: Int,
        pointsPerHand: Int,
        indianRound: Boolean,
    ) {
        _game.value = Game.create(
            playerNames = playerNames,
            pointsPerWin = pointsPerWin,
            pointsPerHand = pointsPerHand,
            indianRound = indianRound,
        )
    }
}
