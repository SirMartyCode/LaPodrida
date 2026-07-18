package com.sirmarty.lapodrida.domain.entities

data class Player(
    val id: Int,
    val name: String,
    val rounds: List<PlayerRound>
)
