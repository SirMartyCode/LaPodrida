package com.sirmarty.lapodrida.ui.screens.gamesettings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sirmarty.lapodrida.ui.components.AppIncrementalNumberInput
import com.sirmarty.lapodrida.ui.components.AppPrimaryButton
import com.sirmarty.lapodrida.ui.components.AppSwitch
import com.sirmarty.lapodrida.ui.components.AppTextField
import com.sirmarty.lapodrida.ui.screens.gamesettings.model.GameSettingsUpdateStrategy
import com.sirmarty.lapodrida.ui.screens.gamesettings.model.PlayersUpdateType
import lapodrida.composeapp.generated.resources.Res
import lapodrida.composeapp.generated.resources.game_settings_indian_round
import lapodrida.composeapp.generated.resources.game_settings_number_of_players
import lapodrida.composeapp.generated.resources.game_settings_player_name_label
import lapodrida.composeapp.generated.resources.game_settings_player_names_title
import lapodrida.composeapp.generated.resources.game_settings_points_per_hand
import lapodrida.composeapp.generated.resources.game_settings_points_per_win
import lapodrida.composeapp.generated.resources.game_settings_settings_title
import lapodrida.composeapp.generated.resources.game_settings_start_game
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun GameSettingsScreen() {
    val viewModel = koinViewModel<GameSettingsViewModel>()
    val state: GameSettingsUiState by viewModel.uiState.collectAsStateWithLifecycle()

    // Update focus requesters list length each time the number of players is changed
    val focusRequesters = remember(state.playerNames.size) {
        List(state.playerNames.size) { FocusRequester() }
    }
    val focusManager = LocalFocusManager.current

    LazyColumn(
        Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Text(
                text = stringResource(Res.string.game_settings_settings_title),
                style = MaterialTheme.typography.titleLarge
            )
            Settings(state = state)
            Spacer(Modifier.height(24.dp))
            Text(
                text = stringResource(Res.string.game_settings_player_names_title),
                style = MaterialTheme.typography.titleLarge
            )
        }
        itemsIndexed(state.playerNames) { index, player ->

            // Last item will have different behavior
            val isLast = index == state.playerNames.size - 1

            AppTextField(
                value = player,
                onValueChange = {
                    state.updateSettings(GameSettingsUpdateStrategy.PlayerName(index, it))
                },
                label = stringResource(Res.string.game_settings_player_name_label, index + 1),
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
            AppPrimaryButton(
                text = stringResource(Res.string.game_settings_start_game),
                onClick = { viewModel.createGame() }
            )
        }
    }
}

@Composable
private fun Settings(state: GameSettingsUiState) = with(state) {
    GameSettingsField(stringResource(Res.string.game_settings_number_of_players)) {
        AppIncrementalNumberInput(
            value = playerNames.size,
            incrementEnabled = canIncrementNumberOfPlayers(),
            decrementEnabled = canDecrementNumberOfPlayers(),
            onValueIncreased = {
                updateSettings(
                    GameSettingsUpdateStrategy.PlayerAmount(PlayersUpdateType.Add)
                )
            },
            onValueDecreased = {
                updateSettings(
                    GameSettingsUpdateStrategy.PlayerAmount(PlayersUpdateType.Remove)
                )
            },
        )
    }
    GameSettingsField(stringResource(Res.string.game_settings_indian_round)) {
        AppSwitch(
            checked = settings.indianRound,
            onCheckedChange = { updateSettings(GameSettingsUpdateStrategy.IndianRound(it)) },
            contentDescription = stringResource(Res.string.game_settings_indian_round),
        )
    }
    GameSettingsField(stringResource(Res.string.game_settings_points_per_win)) {
        AppIncrementalNumberInput(
            value = settings.pointsPerWin,
            incrementEnabled = canIncrementPointsPerWin(),
            decrementEnabled = canDecrementPointsPerWin(),
            onValueUpdated = { updateSettings(GameSettingsUpdateStrategy.PointsPerWin(it)) }
        )
    }
    GameSettingsField(stringResource(Res.string.game_settings_points_per_hand)) {
        AppIncrementalNumberInput(
            value = settings.pointsPerHand,
            incrementEnabled = canIncrementPointsPerHand(),
            decrementEnabled = canDecrementPointsPerHand(),
            onValueUpdated = { updateSettings(GameSettingsUpdateStrategy.PointsPerHand(it)) }
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