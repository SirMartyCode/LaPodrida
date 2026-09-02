package com.sirmarty.lapodrida.domain.entities

import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

data class Game(
    val id: String,
    val players: List<Player>,
    val rounds: List<Round>,
    val currentRoundIndex: Int,
    val pointsPerWin: Int,
    val pointsPerHand: Int,
    val indianRound: Boolean,
    val isFinished: Boolean,
) {
    val currentRound: Round get() = rounds[currentRoundIndex]

    fun updateCurrentRound(transform: (Round) -> Round): Game =
        copy(rounds = rounds.mapIndexed { index, round -> if (index == currentRoundIndex) transform(round) else round })

    companion object {
        @OptIn(ExperimentalUuidApi::class)
        fun create(
            playerNames: List<String>,
            pointsPerWin: Int,
            pointsPerHand: Int,
            indianRound: Boolean,
        ): Game {
            val players = playerNames.mapIndexed { index, name ->
                Player(id = index, name = name)
            }
            val maxRounds: Int = 48 / playerNames.size

            val roundSequence: List<Int> =
                (1..maxRounds).toList() + (maxRounds - 1 downTo 1).toList()

            val fullRoundSequence: List<Int> = if (indianRound) roundSequence + 1 else roundSequence

            val rounds = fullRoundSequence.mapIndexed { index, cardsPerPlayer ->
                Round(
                    roundNumber = index + 1,
                    cardsPerPlayer = cardsPerPlayer,
                    firstPlayerIndex = index % players.size,
                    participations = players.map { player -> RoundParticipation(playerId = player.id) }
                )
            }
            return Game(
                id = Uuid.random().toString(),
                players = players,
                rounds = rounds,
                currentRoundIndex = 0,
                isFinished = false,
                pointsPerWin = pointsPerWin,
                pointsPerHand = pointsPerHand,
                indianRound = indianRound
            )
        }
    }
}
