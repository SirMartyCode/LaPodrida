package com.sirmarty.lapodrida.ui.screens.gamesettings

import com.sirmarty.lapodrida.domain.entities.GameSettings
import com.sirmarty.lapodrida.domain.entities.GameSettings.Companion.MAX_NUMBER_OF_PLAYERS
import com.sirmarty.lapodrida.domain.entities.GameSettings.Companion.MAX_POINTS_PER_HAND
import com.sirmarty.lapodrida.domain.entities.GameSettings.Companion.MAX_POINTS_PER_WIN
import com.sirmarty.lapodrida.domain.entities.GameSettings.Companion.MIN_NUMBER_OF_PLAYERS
import com.sirmarty.lapodrida.domain.entities.GameSettings.Companion.MIN_POINTS_PER_HAND
import com.sirmarty.lapodrida.domain.entities.GameSettings.Companion.MIN_POINTS_PER_WIN
import com.sirmarty.lapodrida.ui.screens.gamesettings.model.GameSettingsUpdateStrategy

data class GameSettingsUiState(
    val settings: GameSettings = GameSettings(),
    val updateSettings: (GameSettingsUpdateStrategy) -> Unit
) {
    val playerNames
        get() = settings.playerNames

    fun canDecrementNumberOfPlayers() = settings.playerNames.size > MIN_NUMBER_OF_PLAYERS
    fun canIncrementNumberOfPlayers() = settings.playerNames.size < MAX_NUMBER_OF_PLAYERS
    fun canDecrementPointsPerWin() = settings.pointsPerWin > MIN_POINTS_PER_WIN
    fun canIncrementPointsPerWin() = settings.pointsPerWin < MAX_POINTS_PER_WIN
    fun canDecrementPointsPerHand() = settings.pointsPerHand > MIN_POINTS_PER_HAND
    fun canIncrementPointsPerHand() = settings.pointsPerHand < MAX_POINTS_PER_HAND

}
