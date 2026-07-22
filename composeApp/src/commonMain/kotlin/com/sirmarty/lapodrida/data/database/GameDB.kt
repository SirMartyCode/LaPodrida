package com.sirmarty.lapodrida.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.sirmarty.lapodrida.domain.entities.Game

@Entity(tableName = "game")
data class GameDB(
    @PrimaryKey val id: String,
    val players: List<PlayerDB>,
    val rounds: List<RoundDB>,
    val currentRoundIndex: Int,
    val isFinished: Boolean,
    val pointsPerWin: Int,
    val pointsPerHand: Int,
    val indianRound: Boolean,
) {
    companion object {
        fun fromDomain(game: Game) = GameDB(
            id = game.id,
            players = game.players.map { PlayerDB.fromDomain(it) },
            rounds = game.rounds.map { RoundDB.fromDomain(it) },
            currentRoundIndex = game.currentRoundIndex,
            isFinished = game.isFinished,
            pointsPerWin = game.pointsPerWin,
            pointsPerHand = game.pointsPerHand,
            indianRound = game.indianRound
        )
    }

    fun toDomain() = Game(
        id = id,
        players = players.map { it.toDomain() },
        rounds = rounds.map { it.toDomain() },
        currentRoundIndex = currentRoundIndex,
        pointsPerWin = pointsPerWin,
        pointsPerHand = pointsPerHand,
        indianRound = indianRound,
        isFinished = isFinished,
    )
}