package com.sirmarty.lapodrida.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sirmarty.lapodrida.domain.entities.Game
import com.sirmarty.lapodrida.ui.theme.LaPodridaTheme

internal const val RowHeaderWidth = 40
internal const val PlayerColumnWidth = 80
internal const val CellHeight = 48
internal const val HeaderHeight = 40

/**
 * Full scoreboard table composing [RoundHeader] + [ScoreCell] into a scrollable grid.
 *
 * - Row-header column (round number + cards) is sticky — does not scroll horizontally.
 * - Player columns scroll horizontally.
 * - Header row is sticky on vertical scroll.
 * - Body scrolls vertically; totals row at the bottom.
 */
@Composable
fun ScoreTable(
    game: Game,
    modifier: Modifier = Modifier
) {
    val hScroll = rememberScrollState()
    val vScroll = rememberScrollState()
    val colors = MaterialTheme.colorScheme

    val totals: List<Int> = game.players.indices.map { playerIdx ->
        game.rounds.sumOf { round -> round.participations[playerIdx].score }
    }
    val maxTotal: Int? = totals.maxOrNull()
    val isFinished = game.isFinished
    val playersWidthDp = game.players.size * PlayerColumnWidth

    Surface(
        modifier = modifier,
        shape = MaterialTheme.shapes.large,
        color = colors.surface,
        tonalElevation = 2.dp
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            // ---- Sticky header row ----
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(HeaderHeight.dp)
                    .background(colors.surfaceVariant),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Corner — fixed
                Box(
                    modifier = Modifier
                        .width(RowHeaderWidth.dp)
                        .height(HeaderHeight.dp)
                        .background(colors.surface),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "#",
                        style = MaterialTheme.typography.labelSmall,
                        color = colors.onSurfaceVariant,
                        textAlign = TextAlign.Center
                    )
                }
                VerticalDivider(color = colors.outlineVariant, thickness = 1.dp)
                // Player headers — horizontally scrollable
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .horizontalScroll(hScroll)
                ) {
                    Row(
                        modifier = Modifier.requiredWidth(playersWidthDp.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        game.players.forEach { player ->
                            Box(
                                modifier = Modifier
                                    .width(PlayerColumnWidth.dp)
                                    .height(HeaderHeight.dp)
                                    .padding(horizontal = 4.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = player.name.ifBlank { "Jugador ${player.id + 1}" },
                                    style = MaterialTheme.typography.labelLarge,
                                    color = colors.onSurface,
                                    textAlign = TextAlign.Center,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                    modifier = Modifier.semantics {
                                        heading()
                                        contentDescription = "Jugador ${player.name}"
                                    }
                                )
                            }
                        }
                    }
                }
            }

            HorizontalDivider(color = colors.outlineVariant, thickness = 1.dp)

            // ---- Body + totals — vertically scrollable ----
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f, fill = false)
                    .verticalScroll(vScroll)
            ) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    // Round rows — Row with sticky left + scrollable right
                    game.rounds.forEachIndexed { roundIndex, round ->
                        val state = resolveCellState(roundIndex, game.currentRoundIndex, isFinished)
                        val isIndianRound = game.indianRound && roundIndex == game.rounds.lastIndex
                        val isCurrentRound = !isFinished && roundIndex == game.currentRoundIndex

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(CellHeight.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            // Sticky RoundHeader
                            RoundHeader(
                                roundNumber = round.roundNumber,
                                cardsPerPlayer = round.cardsPerPlayer,
                                isCurrent = isCurrentRound,
                                isIndianRound = isIndianRound
                            )
                            VerticalDivider(color = colors.outlineVariant, thickness = 1.dp)
                            // Scrollable cells — shares hScroll with header
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .horizontalScroll(hScroll)
                            ) {
                                Row(
                                    modifier = Modifier.requiredWidth(playersWidthDp.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    round.participations.forEach { participation ->
                                        val displayScore: Int? = when (state) {
                                            ScoreCellState.Future -> null
                                            ScoreCellState.Current -> null
                                            ScoreCellState.Completed -> participation.score
                                        }
                                        ScoreCell(
                                            prediction = participation.prediction,
                                            score = displayScore,
                                            state = state,
                                            modifier = Modifier.width(PlayerColumnWidth.dp).height(CellHeight.dp)
                                        )
                                        VerticalDivider(
                                            color = colors.outlineVariant.copy(alpha = 0.5f),
                                            thickness = 1.dp
                                        )
                                    }
                                }
                            }
                        }
                        HorizontalDivider(
                            color = colors.outlineVariant.copy(alpha = 0.4f),
                            thickness = 0.5.dp
                        )
                    }

                    // ---- Totals row (sticky left + scrollable right) ----
                    HorizontalDivider(color = colors.outline, thickness = 1.dp)
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(CellHeight.dp)
                            .background(colors.surfaceVariant),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        TotalsHeader()
                        VerticalDivider(color = colors.outline, thickness = 1.dp)
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .horizontalScroll(hScroll)
                        ) {
                            Row(
                                modifier = Modifier.requiredWidth(playersWidthDp.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                totals.forEach { total ->
                                    val isWinner = isFinished && total == maxTotal
                                    Box(
                                        modifier = Modifier
                                            .width(PlayerColumnWidth.dp)
                                            .height(CellHeight.dp)
                                            .background(
                                                if (isWinner) colors.secondary.copy(alpha = 0.15f)
                                                else colors.surfaceVariant
                                            )
                                            .semantics {
                                                contentDescription = "Total $total" + if (isWinner) ", guanyador" else ""
                                            },
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = total.toString(),
                                            style = MaterialTheme.typography.titleMedium,
                                            color = if (isWinner) colors.secondary else colors.onSurface,
                                            textAlign = TextAlign.Center,
                                            maxLines = 1
                                        )
                                    }
                                    VerticalDivider(
                                        color = colors.outlineVariant.copy(alpha = 0.5f),
                                        thickness = 1.dp
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

private fun resolveCellState(
    roundIndex: Int,
    currentRoundIndex: Int,
    isFinished: Boolean
): ScoreCellState {
    if (isFinished) return ScoreCellState.Completed
    return when {
        roundIndex < currentRoundIndex -> ScoreCellState.Completed
        roundIndex == currentRoundIndex -> ScoreCellState.Current
        else -> ScoreCellState.Future
    }
}

// ---------------- Previews ----------------

private fun previewGame(
    playerNames: List<String> = listOf("Anna", "Pere", "Joan"),
    currentRoundIndex: Int = 2,
    isFinished: Boolean = false
): Game {
    val base = Game.create(
        playerNames = playerNames,
        pointsPerWin = 10,
        pointsPerHand = 3,
        indianRound = true
    )
    val enrichedRounds = base.rounds.mapIndexed { rIdx, round ->
        when {
            rIdx < currentRoundIndex -> round.copy(
                participations = round.participations.mapIndexed { pIdx, p ->
                    p.copy(
                        prediction = (rIdx + pIdx) % (round.cardsPerPlayer + 1),
                        score = if ((rIdx + pIdx) % 2 == 0) 13 else 3,
                        hitPrediction = (rIdx + pIdx) % 2 == 0
                    )
                }
            )
            rIdx == currentRoundIndex -> round.copy(
                participations = round.participations.mapIndexed { pIdx, p ->
                    p.copy(prediction = (pIdx + 1) % (round.cardsPerPlayer + 1), score = 0)
                }
            )
            else -> round
        }
    }
    return base.copy(
        rounds = enrichedRounds,
        currentRoundIndex = currentRoundIndex,
        isFinished = isFinished
    )
}

@Preview(name = "ScoreTable - Mid game 3 players")
@Composable
private fun ScoreTableMidGamePreview() {
    LaPodridaTheme {
        Box(Modifier.padding(8.dp)) {
            ScoreTable(game = previewGame(currentRoundIndex = 3))
        }
    }
}

@Preview(name = "ScoreTable - 4 players start")
@Composable
private fun ScoreTableStartPreview() {
    LaPodridaTheme {
        Box(Modifier.padding(8.dp)) {
            ScoreTable(game = previewGame(playerNames = listOf("Anna", "Pere", "Joan", "Marta"), currentRoundIndex = 0))
        }
    }
}

@Preview(name = "ScoreTable - Finished 2 players")
@Composable
private fun ScoreTableFinishedPreview() {
    LaPodridaTheme {
        Box(Modifier.padding(8.dp)) {
            ScoreTable(game = previewGame(playerNames = listOf("Anna", "Pere"), currentRoundIndex = 0, isFinished = true))
        }
    }
}

@Preview(name = "ScoreTable - 6 players many rounds")
@Composable
private fun ScoreTableManyPlayersPreview() {
    LaPodridaTheme {
        Box(Modifier.padding(8.dp)) {
            ScoreTable(
                game = previewGame(
                    playerNames = listOf("Anna", "Pere", "Joan", "Marta", "Carles", "Núria"),
                    currentRoundIndex = 5
                )
            )
        }
    }
}
