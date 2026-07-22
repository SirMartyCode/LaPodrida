package com.sirmarty.lapodrida.data.database

import com.sirmarty.lapodrida.domain.entities.Round
import kotlinx.serialization.Serializable

@Serializable
data class RoundDB(
    val roundNumber: Int,
    val cardsPerPlayer: Int,
    val firstPlayerIndex: Int,
    val participations: List<RoundParticipationDB>,
) {
    companion object {
        fun fromDomain(round: Round): RoundDB {
            return RoundDB(
                roundNumber = round.roundNumber,
                cardsPerPlayer = round.cardsPerPlayer,
                firstPlayerIndex = round.firstPlayerIndex,
                participations = round.participations.map { RoundParticipationDB.fromDomain(it) },
            )
        }
    }

    fun toDomain() = Round(
        roundNumber = roundNumber,
        cardsPerPlayer = cardsPerPlayer,
        firstPlayerIndex = firstPlayerIndex,
        participations = participations.map { it.toDomain() },
    )
}
