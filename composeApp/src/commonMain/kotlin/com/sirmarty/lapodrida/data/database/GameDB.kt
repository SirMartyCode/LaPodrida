package com.sirmarty.lapodrida.data.database

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.sirmarty.lapodrida.domain.entities.Game

@Entity(tableName = "game")
data class GameDB(
    @PrimaryKey
    val id: String,
    val timestamp: Long,
    @Embedded
    val settings: GameSettingsDB,
    val players: List<PlayerDB>,
    val rounds: List<RoundDB>,
    val currentRoundIndex: Int,
    val isFinished: Boolean
) {
    companion object {
        fun fromDomain(game: Game) =
            GameDB(
                id = game.id,
                timestamp = game.timestamp,
                settings = GameSettingsDB.fromDomain(game.settings),
                players = game.players.map { PlayerDB.fromDomain(it) },
                rounds = game.rounds.map { RoundDB.fromDomain(it) },
                currentRoundIndex = game.currentRoundIndex,
                isFinished = game.isFinished
            )
    }

    fun toDomain() =
        Game(
            id = id,
            timestamp = timestamp,
            settings = settings.toDomain(),
            players = players.map { it.toDomain() },
            rounds = rounds.map { it.toDomain() },
            currentRoundIndex = currentRoundIndex,
            isFinished = isFinished
        )
}