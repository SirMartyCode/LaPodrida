package com.sirmarty.lapodrida.ui.screens.game

/**
 * UI-ready representation of a [com.sirmarty.lapodrida.domain.entities.Game], produced by
 * [GameUiMapper]. Every value here is already resolved (totals, winner flags, per-cell
 * visibility) so composables only need to render it, with no derivation logic of their own.
 */
data class GameUi(
    val statusText: String,
    val isFinished: Boolean,
    val players: List<PlayerUi>,
    val rounds: List<RoundUi>,
)

data class PlayerUi(
    val id: Int,
    val displayName: String,
    val total: Int,
    val isWinner: Boolean,
)

data class RoundUi(
    val roundNumber: Int,
    val cardsPerPlayer: Int,
    val isCurrent: Boolean,
    val isIndianRound: Boolean,
    val cells: List<ScoreCellUi>,
)

data class ScoreCellUi(
    val playerId: Int,
    val prediction: Int?,
    val score: Int?,
    val state: ScoreCellState,
)

/**
 * Represents the visual state of a score cell in the scoreboard.
 */
enum class ScoreCellState {
    /** Round not yet played - dimmed appearance */
    Future,
    /** Currently active round - highlighted with gold accent */
    Current,
    /** Round completed - normal appearance */
    Completed
}
