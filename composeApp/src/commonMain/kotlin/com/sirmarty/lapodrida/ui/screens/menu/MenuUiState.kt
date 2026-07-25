package com.sirmarty.lapodrida.ui.screens.menu

data class MenuUiState(
    val enableContinueButton: Boolean? = null,
    val enableHistoryButton: Boolean? = null,
    val showDeleteGameDialog: Boolean = false,
    val showCurrentGameDeletedDialog: Boolean = false,
    val hideDeleteGameDialog: () -> Unit,
    val hideCurrentGameDeletedDialog: () -> Unit
)
