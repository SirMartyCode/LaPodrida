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
                Text("New Game")
            }
            Button(
                onClick = { onContinueGame() },
                enabled = state.isThereUnfinishedGame == true
            ) {
                if (state.isThereUnfinishedGame == null) {
                    CircularProgressIndicator()
                } else {
                    Text("Continue")
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
                    Text("Game History")
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
        title = {
            Text(text = "Unfinished game deleted")
        },
        text = {
            Text(
                text = "The unfinished game that was saved has been deleted",
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
                    text = "Ok",
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
            Text(text = "Unfinished game")
        },
        text = {
            Text(
                text = "There's an unfinished game saved, if you create a new game the unfinished " +
                        "one will be removed. Are you sure you want to continue?",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                fontSize = 14.sp,
            )
        },
        onDismissRequest = { onDismiss() },
        confirmButton = {
            TextButton(onClick = { onConfirmation() }) {
                Text(
                    text = "Continue",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                )
            }
        },
        dismissButton = {
            TextButton(onClick = { onDismiss() }) {
                Text(
                    text = "Cancel",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                )
            }
        }
    )
}