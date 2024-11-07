package com.sirmarty.lapodrida.data.database

import com.sirmarty.lapodrida.domain.entities.GameSettings

data class GameSettingsDB(
    val numberOfPlayers: Int,
    val isIndianRound: Boolean,
    val pointsPerWin: Int,
    val pointsPerHand: Int,
) {

    companion object {
        fun fromDomain(gameSettings: GameSettings) =
            GameSettingsDB(
                numberOfPlayers = gameSettings.numberOfPlayers,
                isIndianRound = gameSettings.isIndianRound,
                pointsPerWin = gameSettings.pointsPerWin,
                pointsPerHand = gameSettings.pointsPerHand
            )

    }

    fun toDomain() =
        GameSettings(
            numberOfPlayers = numberOfPlayers,
            isIndianRound = isIndianRound,
            pointsPerWin = pointsPerWin,
            pointsPerHand = pointsPerHand
        )
}