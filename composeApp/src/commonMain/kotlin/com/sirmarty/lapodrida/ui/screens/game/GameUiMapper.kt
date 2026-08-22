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

    fun map(game: Game): GameUi =
        GameUi(
            statusText = statusText(game),
            isFinished = game.isFinished,
            players = game.players.map { player -> mapPlayer(player) },
            rounds = game.rounds.mapIndexed { roundIndex, round -> mapRound(game, round, roundIndex) },
        )

    private fun statusText(game: Game): String {
        if (game.isFinished) return "Partida finalitzada"
        val currentRound = game.rounds[game.currentRoundIndex]
        return "Ronda ${game.currentRoundIndex + 1} de ${game.rounds.size}  ·  ${currentRound.cardsPerPlayer} cartes"
    }

    private fun mapPlayer(player: Player): PlayerUi =
        PlayerUi(
            id = player.id,
            displayName = player.name.ifBlank { "Jugador ${player.id + 1}" },
        )

    private fun mapRound(game: Game, round: Round, roundIndex: Int): RoundUi {
        val state = cellState(roundIndex, game.currentRoundIndex, game.isFinished)
        return RoundUi(
            roundNumber = round.roundNumber,
            cardsPerPlayer = round.cardsPerPlayer,
            isCurrent = isCurrentRound(game, roundIndex),
            isIndianRound = isIndianRound(game, roundIndex),
            cells = round.participations.map { participation -> mapCell(participation, state) },
        )
    }

    private fun mapCell(participation: RoundParticipation, state: ScoreCellState): ScoreCellUi =
        ScoreCellUi(
            playerId = participation.playerId,
            prediction = participation.prediction,
            score = displayScore(participation, state),
            state = state,
        )

    private fun displayScore(participation: RoundParticipation, state: ScoreCellState): Int? =
        if (state == ScoreCellState.Completed) participation.score else null

    private fun isCurrentRound(game: Game, roundIndex: Int): Boolean =
        !game.isFinished && roundIndex == game.currentRoundIndex

    private fun isIndianRound(game: Game, roundIndex: Int): Boolean =
        game.indianRound && roundIndex == game.rounds.lastIndex

    private fun cellState(roundIndex: Int, currentRoundIndex: Int, isFinished: Boolean): ScoreCellState {
        if (isFinished) return ScoreCellState.Completed
        return when {
            roundIndex < currentRoundIndex -> ScoreCellState.Completed
            roundIndex == currentRoundIndex -> ScoreCellState.Current
            else -> ScoreCellState.Future
        }
    }
}
