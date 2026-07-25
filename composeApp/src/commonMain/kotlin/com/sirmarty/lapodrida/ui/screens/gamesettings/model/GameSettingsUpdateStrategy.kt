package com.sirmarty.lapodrida.ui.screens.gamesettings.model

import com.sirmarty.lapodrida.domain.entities.GameSettings

sealed class GameSettingsUpdateStrategy(private val updateRule: (GameSettings) -> GameSettings) {

    data class PointsPerWin(val value: Int) : GameSettingsUpdateStrategy({
        it.copy(pointsPerWin = value)
    })

    data class PointsPerHand(val value: Int) : GameSettingsUpdateStrategy({
        it.copy(pointsPerHand = value)
    })

    data class IndianRound(val enabled: Boolean) : GameSettingsUpdateStrategy({
        it.copy(indianRound = enabled)
    })

    data class PlayerAmount(val type: PlayersUpdateType) : GameSettingsUpdateStrategy({
        it.copy(
            playerNames = when (type) {
                PlayersUpdateType.Add -> it.playerNames.toMutableList().apply { this.add("") }
                PlayersUpdateType.Remove -> it.playerNames.toMutableList()
                    .apply { this.removeLast() }
            }
        )
    })

    data class PlayerName(val index: Int, val name: String) : GameSettingsUpdateStrategy({
        it.copy(playerNames = it.playerNames.toMutableList().apply { this[index] = name })
    })

    fun apply(settings: GameSettings): GameSettings = updateRule(settings)
}

enum class PlayersUpdateType {
    Add, Remove
}