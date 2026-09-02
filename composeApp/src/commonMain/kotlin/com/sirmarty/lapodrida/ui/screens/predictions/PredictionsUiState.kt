package com.sirmarty.lapodrida.ui.screens.predictions

data class PredictionsUiState(
    val currentPlayerId: Int,
    val currentPlayerName: String,
    val cardsPerPlayer: Int,
    val handsAlreadyBid: Int,
    val allowedValues: List<Int>,
)

data class PlayerPrediction(
    val playerId: Int,
    val predictedHands: Int
)