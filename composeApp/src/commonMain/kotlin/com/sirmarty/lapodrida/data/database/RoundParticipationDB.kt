package com.sirmarty.lapodrida.data.database

import com.sirmarty.lapodrida.domain.entities.RoundParticipation
import kotlinx.serialization.Serializable

@Serializable
data class RoundParticipationDB(
    val playerId: Int,
    val prediction: Int?,
    val handsWon: Int?,
) {
    companion object {
        fun fromDomain(participation: RoundParticipation): RoundParticipationDB {
            return RoundParticipationDB(
                playerId = participation.playerId,
                prediction = participation.prediction,
                handsWon = participation.handsWon,
            )
        }
    }

    fun toDomain() = RoundParticipation(
        playerId = playerId,
        prediction = prediction,
        handsWon = handsWon,
    )
}
