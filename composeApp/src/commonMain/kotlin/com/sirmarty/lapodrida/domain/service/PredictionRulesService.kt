package com.sirmarty.lapodrida.domain.service

import com.sirmarty.lapodrida.domain.entities.Round

class PredictionRulesService {

    fun getForbiddenValue(round: Round, pendingPredictions: Map<Int, Int>): Int? {
        if (pendingPredictions.size != round.participations.size - 1) return null
        val handsAlreadyBid = pendingPredictions.values.sum()
        val forbidden = round.cardsPerPlayer - handsAlreadyBid
        return forbidden.takeIf { it in 0..round.cardsPerPlayer }
    }

    fun getAllowedValues(round: Round, pendingPredictions: Map<Int, Int>): List<Int> {
        val forbidden = getForbiddenValue(round, pendingPredictions)
        return (0..round.cardsPerPlayer).filter { it != forbidden }
    }
}
