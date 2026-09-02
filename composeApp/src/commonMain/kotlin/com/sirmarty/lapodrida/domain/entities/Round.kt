package com.sirmarty.lapodrida.domain.entities

data class Round(
    val roundNumber: Int,
    val cardsPerPlayer: Int,
    val firstPlayerIndex: Int,
    val participations: List<RoundParticipation>
) {
    val isFullyPredicted: Boolean
        get() = participations.all { it.prediction != null }

    private val playerIds = participations.map { it.playerId }

    val predictionOrder: List<Int>
        get() = playerIds.drop(firstPlayerIndex) + playerIds.take(firstPlayerIndex)


    fun withPredictions(predictions: Map<Int, Int>): Round =
        copy(participations = participations.map { participation ->
            participation.copy(prediction = predictions.getValue(participation.playerId))
        })
}
