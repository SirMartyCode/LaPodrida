package com.sirmarty.lapodrida.ui.screens.game

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI

@OptIn(KoinExperimentalAPI::class)
@Composable
fun GameScreen() {
    val viewModel = koinViewModel<GameViewModel>()
    val state by viewModel.uiState.collectAsState()

    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column {
            Text("GameScreen")
            Spacer(Modifier.height(16.dp))
            Text("${state?.timestamp}")
            Text("${state?.settings?.numberOfPlayers}")
            Text("${state?.settings?.isIndianRound}")
            Text("${state?.settings?.pointsPerWin}")
            Text("${state?.settings?.pointsPerHand}")
            state?.players?.forEach {
                Text(it.name)
            }
        }
    }
}