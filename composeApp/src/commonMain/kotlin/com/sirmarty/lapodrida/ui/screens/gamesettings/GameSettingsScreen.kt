package com.sirmarty.lapodrida.ui.screens.gamesettings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sirmarty.lapodrida.ui.components.IncrementalNumberInput
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI

@OptIn(KoinExperimentalAPI::class)
@Composable
fun GameSettingsScreen(onStartGameClick: () -> Unit) {
    val viewModel = koinViewModel<GameSettingsViewModel>()

    val state by viewModel.uiState.collectAsState()

    Column(
        Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text("Game Settings")

        GameSettingsField("Number of players") {
            IncrementalNumberInput(
                value = state.numberOfPlayers,
                incrementEnabled = state.canIncrementNumberOfPlayers(),
                decrementEnabled = state.canDecrementNumberOfPlayers(),
                onValueUpdated = { viewModel.updateNumberOfPlayers(it) }
            )
        }
        GameSettingsField("Indian round") {
            Switch(
                checked = state.isIndianRound,
                onCheckedChange = { viewModel.updateIsIndianRound(it) },
            )
        }
        GameSettingsField("Points per win") {
            IncrementalNumberInput(
                value = state.pointsPerWin,
                incrementEnabled = state.canIncrementPointsPerWin(),
                decrementEnabled = state.canDecrementPointsPerWin(),
                onValueUpdated = { viewModel.updatePointsPerWin(it) }
            )
        }
        GameSettingsField("Points per hand") {
            IncrementalNumberInput(
                value = state.pointsPerHand,
                incrementEnabled = state.canIncrementPointsPerHand(),
                decrementEnabled = state.canDecrementPointsPerHand(),
                onValueUpdated = { viewModel.updatePointsPerHand(it) }
            )
        }
        Button(onClick = {
            viewModel.save()
            onStartGameClick()
        }) {
            Text("SAVE")
        }
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