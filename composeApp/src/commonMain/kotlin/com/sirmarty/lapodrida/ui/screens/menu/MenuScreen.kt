package com.sirmarty.lapodrida.ui.screens.menu

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Remove
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
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
import org.koin.core.annotation.KoinExperimentalAPI

@OptIn(KoinExperimentalAPI::class)
@Composable
fun MenuScreen(
    onNewGame: () -> Unit,
    onContinueGame: () -> Unit
) {
    val viewModel = koinViewModel<MenuViewModel>()
    val state by viewModel.uiState.collectAsState()

    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(
            Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("MenuScreen")
            Button(onClick = { viewModel.newGame(onNewGameAction = onNewGame) }) {
                Text(stringResource(Res.string.menu_option_new_game))
            }
            Button(
                onClick = { onContinueGame() },
                enabled = state.isThereUnfinishedGame == true
            ) {
                if (state.isThereUnfinishedGame == null) {
                    CircularProgressIndicator()
                } else {
                    Text(stringResource(Res.string.menu_option_continue))
                }
            }
            Button(
                onClick = {
                    // TODO - navigate to finished games screen
                },
                enabled = state.areThereFinishedGames == true
            ) {
                if (state.areThereFinishedGames == null) {
                    CircularProgressIndicator()
                } else {
                    Text(stringResource(Res.string.menu_option_game_history))
                }
            }
        }

        if (state.showDeleteGameDialog) {
            DeleteCurrentGameDialog(
                onConfirmation = {
                    viewModel.hideDeleteGameDialog()
                    viewModel.newGame(delete = true, onNewGameAction = onNewGame)
                },
                onDismiss = { viewModel.hideDeleteGameDialog() }
            )
        }

        if (state.showCurrentGameDeletedDialog) {
            CurrentGameDeletedDialog(
                onConfirmation = { viewModel.hideCurrentGameDeletedDialog(onNewGame) }
            )
        }
    }
}

@Composable
fun CurrentGameDeletedDialog(
    onConfirmation: () -> Unit,
) {
    AlertDialog(
        icon = {
            Icon(
                imageVector = Icons.Rounded.Remove,
                contentDescription = "Example Icon",
            )
        },
        title = { Text(stringResource(Res.string.game_deleted_dialog_title)) },
        text = {
            Text(
                text = stringResource(Res.string.game_deleted_dialog_text),
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                fontSize = 14.sp,
            )
        },
        onDismissRequest = {
            // This dialog cannot be dismissed
        },
        confirmButton = {
            TextButton(onClick = { onConfirmation() }) {
                Text(
                    text = stringResource(Res.string.game_deleted_dialog_confirm),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                )
            }
        }
    )
}


@Composable
fun DeleteCurrentGameDialog(
    onConfirmation: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        icon = {
            Icon(
                imageVector = Icons.Rounded.Remove,
                contentDescription = "Example Icon",
            )
        },
        title = {
            Text(stringResource(Res.string.delete_current_game_dialog_title))
        },
        text = {
            Text(
                text = stringResource(Res.string.delete_current_game_dialog_text),
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                fontSize = 14.sp,
            )
        },
        onDismissRequest = { onDismiss() },
        dismissButton = {
            TextButton(onClick = { onDismiss() }) {
                Text(
                    text = stringResource(Res.string.delete_current_game_dialog_dismiss),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                )
            }
        },
        confirmButton = {
            TextButton(onClick = { onConfirmation() }) {
                Text(
                    text = stringResource(Res.string.delete_current_game_dialog_confirm),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                )
            }
        }
    )
}