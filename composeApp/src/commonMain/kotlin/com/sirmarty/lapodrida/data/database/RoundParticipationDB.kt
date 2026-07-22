package com.sirmarty.lapodrida.data.database

import com.sirmarty.lapodrida.domain.entities.RoundParticipation
import kotlinx.serialization.Serializable

@Serializable
data class RoundParticipationDB(
    val playerId: Int,
    val prediction: Int,
    val handsWon: Int,
    val score: Int,
    val hitPrediction: Boolean,
) {
    companion object {
        fun fromDomain(participation: RoundParticipation): RoundParticipationDB {
            return RoundParticipationDB(
                playerId = participation.playerId,
                prediction = participation.prediction,
                handsWon = participation.handsWon,
                score = participation.score,
                hitPrediction = participation.hitPrediction,
            )
        }
    }

    fun toDomain() = RoundParticipation(
        playerId = playerId,
        prediction = prediction,
        handsWon = handsWon,
        score = score,
        hitPrediction = hitPrediction,
    )
}
