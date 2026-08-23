package com.sirmarty.lapodrida.ui.screens.menu

data class MenuUiState(
    val enableContinueButton: Boolean? = null,
    val enableHistoryButton: Boolean? = null,
    val dialog: MenuDialog? = null,
    val hideDialog: () -> Unit,
)

sealed interface MenuDialog {
    data object ConfirmDeleteSavedGame : MenuDialog
    data object SavedGameDeleted : MenuDialog
}
