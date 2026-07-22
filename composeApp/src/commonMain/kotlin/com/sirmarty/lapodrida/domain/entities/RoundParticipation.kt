package com.sirmarty.lapodrida.domain.entities

data class RoundParticipation(
    val playerId: Int,
    val prediction: Int,
    val handsWon: Int,
    val score: Int,
    val hitPrediction: Boolean
)
