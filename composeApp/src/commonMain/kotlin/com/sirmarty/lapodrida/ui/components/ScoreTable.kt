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
import com.sirmarty.lapodrida.ui.screens.game.GameUi
import com.sirmarty.lapodrida.ui.screens.game.GameUiMapper
import com.sirmarty.lapodrida.ui.theme.LaPodridaTheme

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
    game: GameUi,
    modifier: Modifier = Modifier
) {
    val hScroll = rememberScrollState()
    val vScroll = rememberScrollState()
    val colors = MaterialTheme.colorScheme

    val isFinished = game.isFinished
    val playersWidth = ScoreTableTokens.PlayerColumnWidth * game.players.size

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
                    .height(ScoreTableTokens.HeaderHeight)
                    .background(colors.surfaceVariant),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Corner — fixed
                Box(
                    modifier = Modifier
                        .width(ScoreTableTokens.RowHeaderWidth)
                        .height(ScoreTableTokens.HeaderHeight)
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
                        modifier = Modifier.requiredWidth(playersWidth),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        game.players.forEach { player ->
                            Box(
                                modifier = Modifier
                                    .width(ScoreTableTokens.PlayerColumnWidth)
                                    .height(ScoreTableTokens.HeaderHeight)
                                    .padding(horizontal = 4.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = player.displayName,
                                    style = MaterialTheme.typography.labelLarge,
                                    color = colors.onSurface,
                                    textAlign = TextAlign.Center,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                    modifier = Modifier.semantics {
                                        heading()
                                        contentDescription = "Jugador ${player.displayName}"
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
                    game.rounds.forEach { round ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(ScoreTableTokens.CellHeight),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            // Sticky RoundHeader
                            RoundHeader(
                                title = round.title,
                                subtitle = round.subtitle,
                                state = round.state
                            )
                            VerticalDivider(color = colors.outlineVariant, thickness = 1.dp)
                            // Scrollable cells — shares hScroll with header
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .horizontalScroll(hScroll)
                            ) {
                                Row(
                                    modifier = Modifier.requiredWidth(playersWidth),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    round.cells.forEach { cell ->
                                        ScoreCell(
                                            prediction = cell.prediction,
                                            totalScore = cell.totalScore,
                                            state = round.state
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
                }
            }
        }
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

private val previewMapper = GameUiMapper()

@Preview(name = "ScoreTable - Mid game 3 players")
@Composable
private fun ScoreTableMidGamePreview() {
    LaPodridaTheme {
        Box(Modifier.padding(8.dp)) {
            ScoreTable(game = previewMapper.map(previewGame(currentRoundIndex = 3)))
        }
    }
}

@Preview(name = "ScoreTable - 4 players start")
@Composable
private fun ScoreTableStartPreview() {
    LaPodridaTheme {
        Box(Modifier.padding(8.dp)) {
            ScoreTable(
                game = previewMapper.map(
                    previewGame(playerNames = listOf("Anna", "Pere", "Joan", "Marta"), currentRoundIndex = 0)
                )
            )
        }
    }
}

@Preview(name = "ScoreTable - Finished 2 players")
@Composable
private fun ScoreTableFinishedPreview() {
    LaPodridaTheme {
        Box(Modifier.padding(8.dp)) {
            ScoreTable(
                game = previewMapper.map(
                    previewGame(playerNames = listOf("Anna", "Pere"), currentRoundIndex = 0, isFinished = true)
                )
            )
        }
    }
}

@Preview(name = "ScoreTable - 6 players many rounds")
@Composable
private fun ScoreTableManyPlayersPreview() {
    LaPodridaTheme {
        Box(Modifier.padding(8.dp)) {
            ScoreTable(
                game = previewMapper.map(
                    previewGame(
                        playerNames = listOf("Anna", "Pere", "Joan", "Marta", "Carles", "Núria"),
                        currentRoundIndex = 5
                    )
                )
            )
        }
    }
}
