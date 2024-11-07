package com.sirmarty.lapodrida.data.database

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.sirmarty.lapodrida.domain.entities.Game
import com.sirmarty.lapodrida.domain.entities.Player

@Entity(tableName = "game")
data class GameDB(
    @PrimaryKey
    val timestamp: Long,
    @Embedded
    val settings: GameSettingsDB,
    val players: List<PlayerDB>
) {
    companion object {
        fun fromDomain(game: Game) =
            GameDB(
                timestamp = game.timestamp,
                settings = GameSettingsDB.fromDomain(game.settings),
                players = game.players.map { PlayerDB.fromDomain(it) }
            )
    }

    fun toDomain() =
        Game(
            timestamp = timestamp,
            settings = settings.toDomain(),
            players = players.map { Player(0, it.name, emptyList()) }
        )
}