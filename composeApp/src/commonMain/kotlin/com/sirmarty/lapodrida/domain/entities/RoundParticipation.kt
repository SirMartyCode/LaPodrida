package com.sirmarty.lapodrida.domain.entities

data class RoundParticipation(
    val playerId: Int,
    val prediction: Int? = null,
    val handsWon: Int? = null,
) {
    val hitPrediction: Boolean?
        get() = prediction?.let { it == handsWon }

    val score: Int
        get() = 0 // TODO: real scoring formula (needs Game.pointsPerWin/pointsPerHand) — not implemented yet
}
