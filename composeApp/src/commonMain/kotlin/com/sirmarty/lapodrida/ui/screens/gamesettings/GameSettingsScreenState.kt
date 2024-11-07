package com.sirmarty.lapodrida.ui.screens.gamesettings

import com.sirmarty.lapodrida.domain.entities.GameSettings

data class GameSettingsScreenState(
    val gameSettings: GameSettings = GameSettings(),
    val playerNames: List<String> = List(size = GameSettings.DEFAULT_NUMBER_OF_PLAYERS) { "" },
    val isGameCreated: Boolean = false
)