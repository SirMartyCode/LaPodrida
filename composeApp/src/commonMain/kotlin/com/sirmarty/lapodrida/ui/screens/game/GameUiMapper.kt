package com.sirmarty.lapodrida.ui.screens.game

import com.sirmarty.lapodrida.domain.entities.Game
import com.sirmarty.lapodrida.domain.entities.Player
import com.sirmarty.lapodrida.domain.entities.Round
import com.sirmarty.lapodrida.domain.entities.RoundParticipation

/**
 * Maps the [Game] domain model into a [GameUi] ready to be rendered, resolving every value
 * (totals, winner, per-round/per-cell state) so screen and components stay purely presentational.
 */
class GameUiMapper {

    fun map(game: Game): GameUi {
        val totals = cumulativeTotals(game)
        return GameUi(
            statusText = statusText(game),
            isFinished = game.isFinished,
            hasPendingPredictions = hasPendingPredictions(game),
            players = game.players.map { player -> mapPlayer(player) },
            rounds = game.rounds.mapIndexed { roundIndex, round ->
                mapRound(game, round, roundIndex, totals[roundIndex])
            },
        )
    }

    private fun hasPendingPredictions(game: Game): Boolean =
        !game.isFinished && game.rounds[game.currentRoundIndex].participations.any { it.prediction == null }

    private fun statusText(game: Game): String {
        if (game.isFinished) return "Partida finalitzada"
        val currentRound = game.rounds[game.currentRoundIndex]
        return "Ronda ${game.currentRoundIndex + 1} de ${game.rounds.size}  ·  ${currentRound.cardsPerPlayer} cartes"
    }

    /** Per each round (in order), the running total per player (indexed by playerId) through that round. */
    private fun cumulativeTotals(game: Game): List<IntArray> {
        val running = IntArray(game.players.size)
        return game.rounds.map { round ->
            round.participations.forEach { participation -> running[participation.playerId] += participation.score }
            running.copyOf()
        }
    }

    private fun mapPlayer(player: Player): PlayerUi =
        PlayerUi(
            id = player.id,
            displayName = player.name.ifBlank { "Jugador ${player.id + 1}" },
        )

    private fun mapRound(game: Game, round: Round, roundIndex: Int, totals: IntArray): RoundUi {
        val state = roundState(roundIndex, game.currentRoundIndex, game.isFinished)
        val isIndian = isIndianRound(game, roundIndex)
        return RoundUi(
            title = roundTitle(round, isIndian),
            subtitle = roundSubtitle(round, isIndian),
            state = state,
            cells = round.participations.map { participation -> mapCell(participation, state, totals[participation.playerId]) },
        )
    }

    private fun roundTitle(round: Round, isIndianRound: Boolean): String =
        if (isIndianRound) "IND" else "R${round.roundNumber}"

    private fun roundSubtitle(round: Round, isIndianRound: Boolean): String =
        if (isIndianRound) "Índia" else round.cardsPerPlayer.toString()

    private fun mapCell(participation: RoundParticipation, state: RoundState, cumulativeTotal: Int): ScoreCellUi =
        ScoreCellUi(
            playerId = participation.playerId,
            prediction = participation.prediction,
            totalScore = if (state.isCompleted) cumulativeTotal else null,
        )

    private fun isIndianRound(game: Game, roundIndex: Int): Boolean =
        game.indianRound && roundIndex == game.rounds.lastIndex

    private fun roundState(roundIndex: Int, currentRoundIndex: Int, isFinished: Boolean): RoundState {
        if (isFinished) return RoundState.Completed
        return when {
            roundIndex < currentRoundIndex -> RoundState.Completed
            roundIndex == currentRoundIndex -> RoundState.Current
            else -> RoundState.Future
        }
    }
}
