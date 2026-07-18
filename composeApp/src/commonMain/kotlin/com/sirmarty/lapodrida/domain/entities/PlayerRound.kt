package com.sirmarty.lapodrida.domain.entities

data class PlayerRound(
    val playerId: Int,
    val roundNumber: Int,
    val handsAsked: Int,
    val handsWon: Int
)
