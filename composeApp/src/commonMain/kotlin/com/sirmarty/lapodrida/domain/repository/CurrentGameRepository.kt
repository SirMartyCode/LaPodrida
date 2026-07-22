package com.sirmarty.lapodrida.domain.repository

import com.sirmarty.lapodrida.domain.entities.Game
import kotlinx.coroutines.flow.StateFlow

interface CurrentGameRepository {
    val game: StateFlow<Game?>

    fun startNewGame(
        playerNames: List<String>,
        pointsPerWin: Int,
        pointsPerHand: Int,
        indianRound: Boolean,
    )
}
