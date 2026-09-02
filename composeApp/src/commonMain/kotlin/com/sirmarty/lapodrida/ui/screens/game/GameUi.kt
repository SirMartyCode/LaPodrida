package com.sirmarty.lapodrida.ui.screens.game

/**
 * UI-ready representation of a [com.sirmarty.lapodrida.domain.entities.Game], produced by
 * [GameUiMapper]. Every value here is already resolved (totals, winner flags, per-cell
 * visibility) so composables only need to render it, with no derivation logic of their own.
 */
data class GameUi(
    val statusText: String,
    val isFinished: Boolean,
    val hasPendingPredictions: Boolean,
    val players: List<PlayerUi>,
    val rounds: List<RoundUi>,
)

data class PlayerUi(
    val id: Int,
    val displayName: String,
)

data class RoundUi(
    val title: String,
    val subtitle: String,
    val state: RoundState,
    val cells: List<ScoreCellUi>,
)

data class ScoreCellUi(
    val playerId: Int,
    val prediction: Int?,
    val totalScore: Int?,
)

/**
 * Represents the visual state of a round (and, by extension, every cell in its row).
 */
sealed interface RoundState {
    data object Future: RoundState
    data object Current: RoundState
    data object Completed: RoundState

    val isCurrent: Boolean
        get() = this == Current

    val isCompleted: Boolean
        get() = this == Completed
}
