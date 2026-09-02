package com.sirmarty.lapodrida.ui.screens.game

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sirmarty.lapodrida.ui.components.AppPrimaryButton
import com.sirmarty.lapodrida.ui.components.ScoreTable
import lapodrida.composeapp.generated.resources.Res
import lapodrida.composeapp.generated.resources.game_enter_predictions_button
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun GameScreen() {
    val viewModel = koinViewModel<GameViewModel>()
    val game: GameUi? by viewModel.uiState.collectAsStateWithLifecycle()

    game?.let {
        GameContent(
            game = it,
            onEnterPredictionsClicked = viewModel::onEnterPredictionsClicked
        )
    } ?: LoadingState()
}

@Composable
private fun GameContent(
    game: GameUi,
    onEnterPredictionsClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(12.dp)
    ) {
        // Header: game title + round indicator
        Text(
            text = "La Podrida",
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth(),
        )
        Text(
            text = game.statusText,
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 4.dp, bottom = 12.dp),
        )

        ScoreTable(
            game = game,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        )

        if (game.hasPendingPredictions) {
            AppPrimaryButton(
                text = stringResource(Res.string.game_enter_predictions_button),
                onClick = onEnterPredictionsClicked,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp),
            )
        }
    }
}

@Composable
private fun LoadingState(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            CircularProgressIndicator(color = MaterialTheme.colorScheme.secondary)
            Text(
                text = "Carregant partida…",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(top = 12.dp)
            )
        }
    }
}
