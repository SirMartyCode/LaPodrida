package com.sirmarty.lapodrida.domain.entities

data class GameSettings(
    var numberOfPlayers: Int = DEFAULT_NUMBER_OF_PLAYERS,
    var isIndianRound: Boolean = DEFAULT_IS_INDIAN_ROUND,
    var pointsPerWin: Int = DEFAULT_POINTS_PER_WIN,
    var pointsPerHand: Int = DEFAULT_POINTS_PER_HAND,
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

    fun canDecrementNumberOfPlayers(): Boolean {
        return numberOfPlayers > MIN_NUMBER_OF_PLAYERS
    }

    fun canIncrementNumberOfPlayers(): Boolean {
        return numberOfPlayers < MAX_NUMBER_OF_PLAYERS
    }

    fun canDecrementPointsPerWin(): Boolean {
        return pointsPerWin > MIN_POINTS_PER_WIN
    }

    fun canIncrementPointsPerWin(): Boolean {
        return pointsPerWin < MAX_POINTS_PER_WIN
    }

    fun canDecrementPointsPerHand(): Boolean {
        return pointsPerHand > MIN_POINTS_PER_HAND
    }

    fun canIncrementPointsPerHand(): Boolean {
        return pointsPerHand < MAX_POINTS_PER_HAND
    }
}