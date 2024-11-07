package com.sirmarty.lapodrida.ui.screens.gamesettings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.sirmarty.lapodrida.domain.entities.GameSettings
import com.sirmarty.lapodrida.ui.components.IncrementalNumberInput
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI

@OptIn(KoinExperimentalAPI::class)
@Composable
fun GameSettingsScreen(onStartGame: () -> Unit) {
    val viewModel = koinViewModel<GameSettingsViewModel>()
    val state by viewModel.uiState.collectAsState()

    if (state.isGameCreated) {
        onStartGame()
    }

    // Update focus requesters list length each time the number of players is changed
    val focusRequesters = remember(state.gameSettings.numberOfPlayers) {
        List(state.gameSettings.numberOfPlayers) { FocusRequester() }
    }
    val focusManager = LocalFocusManager.current

    LazyColumn(
        Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Text("Game Settings")
            Settings(
                gameSettings = state.gameSettings,
                onNumberOfPlayersIncreased = { viewModel.increaseNumberOfPlayers() },
                onNumberOfPlayersDecreased = { viewModel.decreaseNumberOfPlayers() },
                onIsIndianRoundUpdated = { viewModel.updateIsIndianRound(it) },
                onPointsPerWinUpdated = { viewModel.updatePointsPerWin(it) },
                onPointsPerHandUpdated = { viewModel.updatePointsPerHand(it) }
            )
            Spacer(Modifier.height(24.dp))
            Text("Player Names")
        }
        items(state.gameSettings.numberOfPlayers) { index ->

            // Last item will have different behaviour
            val isLast = index == state.gameSettings.numberOfPlayers - 1

            TextField(
                value = state.playerNames[index],
                onValueChange = {
                    viewModel.updatePlayerName(index, it)
                },
                label = { Text("Player $index") },
                maxLines = 1,
                singleLine = true,
                modifier = Modifier.fillMaxWidth().focusRequester(focusRequesters[index]),
                keyboardOptions = KeyboardOptions.Default.copy(
                    // Hide keyboard when last item is done
                    // Continue writing when any other item is done
                    imeAction = if (isLast) ImeAction.Done else ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    // Move focus to the next TextField
                    onNext = { focusRequesters[index + 1].requestFocus() },
                    // Clear focus and hide keyboard
                    onDone = { focusManager.clearFocus() }
                )
            )
        }
        item {
            Spacer(Modifier.height(24.dp))
            Button(onClick = { viewModel.createGame() }) {
                Text("Start game")
            }
        }
    }
}

@Composable
private fun Settings(
    gameSettings: GameSettings,
    onNumberOfPlayersIncreased: () -> Unit,
    onNumberOfPlayersDecreased: () -> Unit,
    onIsIndianRoundUpdated: (Boolean) -> Unit,
    onPointsPerWinUpdated: (Int) -> Unit,
    onPointsPerHandUpdated: (Int) -> Unit
) {
    GameSettingsField("Number of players") {
        IncrementalNumberInput(
            value = gameSettings.numberOfPlayers,
            incrementEnabled = gameSettings.canIncrementNumberOfPlayers(),
            decrementEnabled = gameSettings.canDecrementNumberOfPlayers(),
            onValueIncreased = onNumberOfPlayersIncreased,
            onValueDecreased = onNumberOfPlayersDecreased,
        )
    }
    GameSettingsField("Indian round") {
        Switch(
            checked = gameSettings.isIndianRound,
            onCheckedChange = onIsIndianRoundUpdated
        )
    }
    GameSettingsField("Points per win") {
        IncrementalNumberInput(
            value = gameSettings.pointsPerWin,
            incrementEnabled = gameSettings.canIncrementPointsPerWin(),
            decrementEnabled = gameSettings.canDecrementPointsPerWin(),
            onValueUpdated = onPointsPerWinUpdated
        )
    }
    GameSettingsField("Points per hand") {
        IncrementalNumberInput(
            value = gameSettings.pointsPerHand,
            incrementEnabled = gameSettings.canIncrementPointsPerHand(),
            decrementEnabled = gameSettings.canDecrementPointsPerHand(),
            onValueUpdated = onPointsPerHandUpdated
        )
    }
}

@Composable
private fun GameSettingsField(fieldText: String, fieldInput: @Composable () -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(fieldText, modifier = Modifier.weight(1f))
        fieldInput()
    }
}