package com.sirmarty.lapodrida.ui.screens.gamesettings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sirmarty.lapodrida.domain.entities.GameSettings
import com.sirmarty.lapodrida.ui.components.IncrementalNumberInput
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI

@OptIn(KoinExperimentalAPI::class)
@Composable
fun GameSettingsScreen(onStartGameClick: () -> Unit) {
    val viewModel = koinViewModel<GameSettingsViewModel>()

    val state by viewModel.uiState.collectAsState()

    LazyColumn(
        Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item { Text("Game Settings") }
        item {
            Settings(
                gameSettings = state.gameSettings,
                onNumberOfPlayersIncreased = { viewModel.increaseNumberOfPlayers() },
                onNumberOfPlayersDecreased = { viewModel.decreaseNumberOfPlayers() },
                onIsIndianRoundUpdated = { viewModel.updateIsIndianRound(it) },
                onPointsPerWinUpdated = { viewModel.updatePointsPerWin(it) },
                onPointsPerHandUpdated = { viewModel.updatePointsPerHand(it) }
            )
        }
        items(state.gameSettings.numberOfPlayers) { index ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text("Player $index", modifier = Modifier.weight(1f))
                TextField(
                    value = state.playerNames[index],
                    onValueChange = {
                        viewModel.updatePlayerName(index, it)
                    },
                    maxLines = 1,

                )
            }
        }
        item {
            Button(onClick = {viewModel.save()}) {
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