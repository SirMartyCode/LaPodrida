package com.sirmarty.lapodrida.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.sirmarty.lapodrida.domain.models.GameSettingsModel

@Entity(tableName = "gameSettings")
data class GameSettingsDB(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val numberOfPlayers: Int,
    val isIndianRound: Boolean,
    val pointsPerWin: Int,
    val pointsPerHand: Int,
) {

    companion object {
        fun fromDomain(gameSettings: GameSettingsModel) =
            GameSettingsDB(
                id = 0,
                numberOfPlayers = gameSettings.numberOfPlayers,
                isIndianRound = gameSettings.isIndianRound,
                pointsPerWin = gameSettings.pointsPerWin,
                pointsPerHand = gameSettings.pointsPerHand
            )

    }

    fun toDomain() =
        GameSettingsModel(
            numberOfPlayers = numberOfPlayers,
            isIndianRound = isIndianRound,
            pointsPerWin = pointsPerWin,
            pointsPerHand = pointsPerHand
        )

}