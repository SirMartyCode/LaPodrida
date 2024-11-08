package com.sirmarty.lapodrida.ui.screens.menu

data class MenuScreenState(
    val isThereUnfinishedGame: Boolean? = null,
    val areThereFinishedGames: Boolean? = null,
    val showDeleteGameDialog: Boolean = false,
    val showCurrentGameDeletedDialog: Boolean = false
)
