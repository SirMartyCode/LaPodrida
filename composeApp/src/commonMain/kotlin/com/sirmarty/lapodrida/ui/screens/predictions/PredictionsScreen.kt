package com.sirmarty.lapodrida.ui.screens.predictions

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sirmarty.lapodrida.ui.components.AppIncrementalNumberInput
import com.sirmarty.lapodrida.ui.components.AppPrimaryButton
import lapodrida.composeapp.generated.resources.Res
import lapodrida.composeapp.generated.resources.predictions_confirm
import lapodrida.composeapp.generated.resources.predictions_hands_in_round
import lapodrida.composeapp.generated.resources.predictions_player_label
import lapodrida.composeapp.generated.resources.predictions_progress
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun PredictionsScreen() {
    val viewModel = koinViewModel<PredictionsViewModel>()
    val state: PredictionsUiState? by viewModel.uiState.collectAsStateWithLifecycle()

    state?.let { PredictionsContent(state = it, onConfirm = viewModel::confirmPrediction) }
}

@Composable
private fun PredictionsContent(
    state: PredictionsUiState,
    onConfirm: (Int, Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    var selectedValueIndex by remember(state.currentPlayerId) { mutableIntStateOf(0) }
    val currentValue = state.allowedValues[selectedValueIndex]

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            text = stringResource(Res.string.predictions_hands_in_round, state.cardsPerPlayer),
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth(),
        )
        Text(
            text = stringResource(
                Res.string.predictions_progress,
                state.handsAlreadyBid,
                state.cardsPerPlayer,
            ),
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth(),
        )

        Spacer(Modifier.height(24.dp))

        Text(
            text = stringResource(Res.string.predictions_player_label, state.currentPlayerName),
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth(),
        )

        Spacer(Modifier.height(24.dp))

        AppIncrementalNumberInput(
            value = currentValue,
            incrementEnabled = selectedValueIndex < state.allowedValues.lastIndex,
            decrementEnabled = selectedValueIndex > 0,
            onValueIncreased = { selectedValueIndex++ },
            onValueDecreased = { selectedValueIndex-- },
        )

        Spacer(Modifier.height(24.dp))

        AppPrimaryButton(
            text = stringResource(Res.string.predictions_confirm),
            onClick = { onConfirm(currentValue, state.currentPlayerId) },
        )
    }
}
