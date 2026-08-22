package com.sirmarty.lapodrida.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sirmarty.lapodrida.ui.screens.game.RoundState
import com.sirmarty.lapodrida.ui.theme.LaPodridaTheme

/**
 * A scoreboard cell split into two sections: prediction (left, narrow) and total score (right, wide).
 *
 * Displays a vertical divider between sections. Adapts appearance based on [state]:
 * - [Future]: Surface background, muted/dimmed (alpha ~0.5), shows "—" for both values
 * - [Current]: Secondary (gold) background, onSecondary text, highlighted
 * - [Completed]: SurfaceVariant background, prediction muted, total score prominent
 *
 * Null values display as "—" (em dash) rather than "0" to distinguish unentered from zero.
 *
 * @param prediction The predicted tricks ("mans demanades"), null if not entered yet
 * @param totalScore The player's cumulative score through this round, null if not scored yet
 * @param state Visual state determining styling
 * @param modifier Optional modifier for layout customization
 */
@Composable
fun ScoreCell(
    prediction: Int?,
    totalScore: Int?,
    state: RoundState,
    modifier: Modifier = Modifier
) {
    val colors = MaterialTheme.colorScheme
    val typography = MaterialTheme.typography

    // Resolve colors and alphas based on state
    val backgroundColor: androidx.compose.ui.graphics.Color
    val predictionTextColor: androidx.compose.ui.graphics.Color
    val scoreTextColor: androidx.compose.ui.graphics.Color
    val dividerColor: androidx.compose.ui.graphics.Color
    val contentAlpha: Float
    when (state) {
        RoundState.Future -> {
            backgroundColor = colors.surface
            predictionTextColor = colors.onSurfaceVariant.copy(alpha = 0.5f)
            scoreTextColor = colors.onSurfaceVariant.copy(alpha = 0.5f)
            dividerColor = colors.outlineVariant.copy(alpha = 0.3f)
            contentAlpha = 0.5f
        }
        RoundState.Current -> {
            backgroundColor = colors.secondary
            predictionTextColor = colors.onSecondary
            scoreTextColor = colors.onSecondary
            dividerColor = colors.onSecondary.copy(alpha = 0.3f)
            contentAlpha = 1f
        }
        RoundState.Completed -> {
            backgroundColor = colors.surfaceVariant
            predictionTextColor = colors.onSurfaceVariant
            scoreTextColor = colors.onSurface
            dividerColor = colors.outlineVariant
            contentAlpha = 1f
        }
    }

    val predictionText = prediction?.toString() ?: "—"
    val totalScoreText = totalScore?.toString() ?: "—"

    // Build content description for accessibility
    val contentDescription = when (state) {
        RoundState.Future -> "Future round — no data entered"
        RoundState.Current -> {
            val predDesc = if (prediction != null) "prediction $prediction" else "no prediction"
            val scoreDesc = if (totalScore != null) "total score $totalScore" else "not scored yet"
            "Current round, $predDesc, $scoreDesc"
        }
        RoundState.Completed -> {
            val predDesc = if (prediction != null) "prediction $prediction" else "no prediction"
            val scoreDesc = if (totalScore != null) "total score $totalScore" else "no score"
            "Completed round, $predDesc, $scoreDesc"
        }
    }

    Box(
        modifier = modifier
            .width(ScoreTableTokens.PlayerColumnWidth)
            .height(ScoreTableTokens.CellHeight)
            .background(backgroundColor),
            //.semantics {  contentDescription= contentDescription },
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(4.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Left section: Prediction (narrow, fixed width ~40dp)
            Box(
                modifier = Modifier
                    .width(24.dp)
                    .fillMaxHeight(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = predictionText,
                    style = typography.labelMedium,
                    color = predictionTextColor.copy(alpha = contentAlpha),
                    textAlign = TextAlign.Center,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            // Vertical divider
            Box(
                modifier = Modifier
                    .size(width = 1.dp, height = 24.dp)
                    .background(dividerColor.copy(alpha = contentAlpha))
            )

            // Right section: Score (fills remaining width)
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = totalScoreText,
                    style = typography.bodyLarge,
                    color = scoreTextColor.copy(alpha = contentAlpha),
                    textAlign = TextAlign.Start,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

@Preview(name = "ScoreCell - Future")
@Composable
private fun ScoreCellFuturePreview() {
    LaPodridaTheme {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            ScoreCell(
                prediction = null,
                totalScore = null,
                state = RoundState.Future
            )
            ScoreCell(
                prediction = 2,
                totalScore = null,
                state = RoundState.Future
            )
        }
    }
}

@Preview(name = "ScoreCell - Current")
@Composable
private fun ScoreCellCurrentPreview() {
    LaPodridaTheme {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            ScoreCell(
                prediction = 12,
                totalScore = null,
                state = RoundState.Current
            )
            ScoreCell(
                prediction = 3,
                totalScore = 5,
                state = RoundState.Current
            )
            ScoreCell(
                prediction = 0,
                totalScore = null,
                state = RoundState.Current
            )
        }
    }
}

@Preview(name = "ScoreCell - Completed")
@Composable
private fun ScoreCellCompletedPreview() {
    LaPodridaTheme {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            ScoreCell(
                prediction = 2,
                totalScore = 5,
                state = RoundState.Completed
            )
            ScoreCell(
                prediction = 3,
                totalScore = 3,
                state = RoundState.Completed
            )
            ScoreCell(
                prediction = 0,
                totalScore = 0,
                state = RoundState.Completed
            )
            ScoreCell(
                prediction = null,
                totalScore = 10,
                state = RoundState.Completed
            )
        }
    }
}

@Preview(name = "ScoreCell - All States")
@Composable
private fun ScoreCellAllStatesPreview() {
    LaPodridaTheme {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Future row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                ScoreCell(prediction = null, totalScore = null, state = RoundState.Future)
                ScoreCell(prediction = null, totalScore = null, state = RoundState.Future)
                ScoreCell(prediction = null, totalScore = null, state = RoundState.Future)
            }

            // Current row (highlighted)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                ScoreCell(prediction = 2, totalScore = null, state = RoundState.Current)
                ScoreCell(prediction = 3, totalScore = null, state = RoundState.Current)
                ScoreCell(prediction = 1, totalScore = null, state = RoundState.Current)
            }

            // Completed rows
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                ScoreCell(prediction = 2, totalScore = 5, state = RoundState.Completed)
                ScoreCell(prediction = 3, totalScore = 3, state = RoundState.Completed)
                ScoreCell(prediction = 1, totalScore = 0, state = RoundState.Completed)
            }
        }
    }
}