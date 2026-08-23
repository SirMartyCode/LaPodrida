package com.sirmarty.lapodrida.ui.screens.menu

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.History
import androidx.compose.material.icons.rounded.Language
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sirmarty.lapodrida.domain.entities.Language
import com.sirmarty.lapodrida.ui.components.AppDialog
import com.sirmarty.lapodrida.ui.components.AppIconButton
import com.sirmarty.lapodrida.ui.components.AppSecondaryButton
import com.sirmarty.lapodrida.ui.components.AppTitle
import lapodrida.composeapp.generated.resources.Res
import lapodrida.composeapp.generated.resources.delete_current_game_dialog_confirm
import lapodrida.composeapp.generated.resources.delete_current_game_dialog_dismiss
import lapodrida.composeapp.generated.resources.delete_current_game_dialog_text
import lapodrida.composeapp.generated.resources.delete_current_game_dialog_title
import lapodrida.composeapp.generated.resources.game_deleted_dialog_confirm
import lapodrida.composeapp.generated.resources.game_deleted_dialog_text
import lapodrida.composeapp.generated.resources.game_deleted_dialog_title
import lapodrida.composeapp.generated.resources.menu_option_continue
import lapodrida.composeapp.generated.resources.menu_option_game_history
import lapodrida.composeapp.generated.resources.menu_option_new_game
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun MenuScreen() {
    val viewModel = koinViewModel<MenuViewModel>()
    val state: MenuUiState by viewModel.uiState.collectAsStateWithLifecycle()

    Box(Modifier.fillMaxSize()) {
        LanguagePicker(
            modifier = Modifier.align(Alignment.TopEnd).padding(8.dp),
            onLanguageClick = { viewModel.changeLanguage(it) }
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 32.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AppTitle(text = "La Podrida")
            Spacer(Modifier.height(48.dp))
            AppSecondaryButton(
                text = stringResource(Res.string.menu_option_new_game),
                icon = Icons.Rounded.Add,
                onClick = { viewModel.newGame() }
            )
            Spacer(Modifier.height(16.dp))
            AppSecondaryButton(
                text = stringResource(Res.string.menu_option_continue),
                icon = Icons.Rounded.PlayArrow,
                onClick = { viewModel.continueGame() },
                enabled = state.enableContinueButton == true,
                loading = state.enableContinueButton == null
            )
            Spacer(Modifier.height(16.dp))
            AppSecondaryButton(
                text = stringResource(Res.string.menu_option_game_history),
                icon = Icons.Rounded.History,
                onClick = { /* TODO - navigate to finished games screen */ },
                enabled = state.enableHistoryButton == true,
                loading = state.enableHistoryButton == null
            )
        }

        when (state.dialog) {
            MenuDialog.ConfirmDeleteSavedGame -> AppDialog(
                title = stringResource(Res.string.delete_current_game_dialog_title),
                text = stringResource(Res.string.delete_current_game_dialog_text),
                confirmText = stringResource(Res.string.delete_current_game_dialog_confirm),
                onConfirm = {
                    state.hideDialog()
                    viewModel.newGame(delete = true)
                },
                dismissText = stringResource(Res.string.delete_current_game_dialog_dismiss),
                onDismiss = { state.hideDialog() }
            )

            MenuDialog.SavedGameDeleted -> AppDialog(
                title = stringResource(Res.string.game_deleted_dialog_title),
                text = stringResource(Res.string.game_deleted_dialog_text),
                confirmText = stringResource(Res.string.game_deleted_dialog_confirm),
                onConfirm = {
                    state.hideDialog()
                    viewModel.newGame(delete = false)
                }
            )

            null -> {}
        }
    }
}

@Composable
private fun LanguagePicker(modifier: Modifier = Modifier, onLanguageClick: (Language) -> Unit) {
    var expanded by remember { mutableStateOf(false) }

    Box(modifier = modifier) {
        AppIconButton(
            icon = Icons.Rounded.Language,
            contentDescription = "Select language",
            onClick = { expanded = true }
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            DropdownMenuItem(
                text = { Text("English") },
                onClick = {
                    expanded = false
                    onLanguageClick(Language.English)
                }
            )
            DropdownMenuItem(
                text = { Text("Español") },
                onClick = {
                    expanded = false
                    onLanguageClick(Language.Spanish)
                }
            )
            DropdownMenuItem(
                text = { Text("Català") },
                onClick = {
                    expanded = false
                    onLanguageClick(Language.Catalan)
                }
            )
        }
    }
}
