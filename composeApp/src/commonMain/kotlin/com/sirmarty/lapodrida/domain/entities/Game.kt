package com.sirmarty.lapodrida.domain.entities

data class Game(
    val id: String,
    val timestamp: Long,
    val settings: GameSettings,
    val players: List<Player>,
    val rounds: List<Round> = emptyList(),
    val currentRoundIndex: Int = 0,
    val isFinished: Boolean = false
)
