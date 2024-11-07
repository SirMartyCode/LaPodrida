package com.sirmarty.lapodrida.domain.entities

data class Game(
    val timestamp: Long,
    val settings: GameSettings,
    val players: List<Player>
)