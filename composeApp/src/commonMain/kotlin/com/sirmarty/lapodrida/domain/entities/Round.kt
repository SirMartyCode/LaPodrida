package com.sirmarty.lapodrida.domain.entities

data class Round(
    val roundNumber: Int,
    val cardsPerPlayer: Int,
    val firstPlayerIndex: Int,
    val participations: List<RoundParticipation>
)
