package com.sirmarty.lapodrida.data.database

import com.sirmarty.lapodrida.domain.entities.Player
import kotlinx.serialization.Serializable

@Serializable
data class PlayerDB(
    val name: String,
) {
    companion object {
        fun fromDomain(player: Player): PlayerDB {
            return PlayerDB(name = player.name)
        }
    }
}