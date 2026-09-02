package com.sirmarty.lapodrida.ui.screens.predictions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sirmarty.lapodrida.domain.entities.Player
import com.sirmarty.lapodrida.domain.entities.Round
import com.sirmarty.lapodrida.domain.repository.CurrentGameRepository
import com.sirmarty.lapodrida.domain.service.PredictionRulesService
import com.sirmarty.lapodrida.ui.navigation.Navigator
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

class PredictionsViewModel(
    private val navigator: Navigator,
    private val currentGameRepository: CurrentGameRepository,
    private val predictionRules: PredictionRulesService,
) : ViewModel() {

    private val playerPredictions = MutableStateFlow<Map<Int, Int>>(emptyMap())

    private val currentRound = currentGameRepository.game
        .filterNotNull()
        .map { it.currentRound }


    val uiState: StateFlow<PredictionsUiState?> = combine(
        currentGameRepository.game, playerPredictions
    ) { game, predictions -> game?.let { toUiState(it.currentRound, it.players, predictions) } }
        .stateIn(viewModelScope, SharingStarted.Lazily, null)

    fun confirmPrediction(value: Int, playerId: Int) {
        playerPredictions.update { it.plus(playerId to value) }


        //TODO: send when equals
        /*
        val game = currentGameRepository.game.value ?: return
        if (updated.size == game.currentRound.participations.size) {
            viewModelScope.launch {
                currentGameRepository.submitPredictions(updated)
                navigator.goBack()
            }
        }

         */
    }

    private fun toUiState(
        round: Round,
        players: List<Player>,
        predictions: Map<Int, Int>
    ): PredictionsUiState {
        val currentPlayerId = round.predictionOrder[predictions.size]
        val currentPlayer = players.first { it.id == currentPlayerId }
        return PredictionsUiState(
            currentPlayerId = currentPlayerId,
            currentPlayerName = currentPlayer.name,
            cardsPerPlayer = round.cardsPerPlayer,
            handsAlreadyBid = predictions.values.sum(),
            allowedValues = predictionRules.getAllowedValues(round, predictions),
        )
    }
}
