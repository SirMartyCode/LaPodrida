package com.sirmarty.lapodrida.ui.screens.game

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sirmarty.lapodrida.domain.entities.Game
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun GameScreen() {
    val viewModel = koinViewModel<GameViewModel>()
    val state: Game? by viewModel.uiState.collectAsStateWithLifecycle()

    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column {
            Text("GameScreen")
            Spacer(Modifier.height(16.dp))
            Text("${state?.id}")
            Text("${state?.pointsPerWin}")
            Text("${state?.pointsPerHand}")
            state?.players?.forEach {
                Text(it.name)
            }
        }
    }
}