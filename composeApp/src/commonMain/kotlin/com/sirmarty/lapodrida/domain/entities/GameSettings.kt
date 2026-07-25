package com.sirmarty.lapodrida.domain.entities

data class GameSettings(
    val indianRound: Boolean = DEFAULT_IS_INDIAN_ROUND,
    val pointsPerWin: Int = DEFAULT_POINTS_PER_WIN,
    val pointsPerHand: Int = DEFAULT_POINTS_PER_HAND,
    val playerNames: List<String> = List(DEFAULT_NUMBER_OF_PLAYERS) { "" },
) {
    companion object {
        const val DEFAULT_NUMBER_OF_PLAYERS = 4
        const val DEFAULT_IS_INDIAN_ROUND = true
        const val DEFAULT_POINTS_PER_WIN = 10
        const val DEFAULT_POINTS_PER_HAND = 3

        const val MIN_NUMBER_OF_PLAYERS = 2
        const val MAX_NUMBER_OF_PLAYERS = 12
        const val MIN_POINTS_PER_WIN = 5
        const val MAX_POINTS_PER_WIN = 30
        const val MIN_POINTS_PER_HAND = 1
        const val MAX_POINTS_PER_HAND = 10
    }
}