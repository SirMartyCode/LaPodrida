package com.sirmarty.lapodrida.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sirmarty.lapodrida.ui.theme.LaPodridaTheme

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

/**
 * A scoreboard cell split into two sections: prediction (left, narrow) and score (right, wide).
 *
 * Displays a vertical divider between sections. Adapts appearance based on [state]:
 * - [Future]: Surface background, muted/dimmed (alpha ~0.5), shows "—" for both values
 * - [Current]: Secondary (gold) background, onSecondary text, highlighted
 * - [Completed]: SurfaceVariant background, prediction muted, score prominent
 *
 * Null values display as "—" (em dash) rather than "0" to distinguish unentered from zero.
 *
 * @param prediction The predicted tricks ("mans demanades"), null if not entered yet
 * @param score The actual round score, null if not scored yet
 * @param state Visual state determining styling
 * @param modifier Optional modifier for layout customization
 */
@Composable
fun ScoreCell(
    prediction: Int?,
    score: Int?,
    state: ScoreCellState,
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
        ScoreCellState.Future -> {
            backgroundColor = colors.surface
            predictionTextColor = colors.onSurfaceVariant.copy(alpha = 0.5f)
            scoreTextColor = colors.onSurfaceVariant.copy(alpha = 0.5f)
            dividerColor = colors.outlineVariant.copy(alpha = 0.3f)
            contentAlpha = 0.5f
        }
        ScoreCellState.Current -> {
            backgroundColor = colors.secondary
            predictionTextColor = colors.onSecondary
            scoreTextColor = colors.onSecondary
            dividerColor = colors.onSecondary.copy(alpha = 0.3f)
            contentAlpha = 1f
        }
        ScoreCellState.Completed -> {
            backgroundColor = colors.surfaceVariant
            predictionTextColor = colors.onSurfaceVariant
            scoreTextColor = colors.onSurface
            dividerColor = colors.outlineVariant
            contentAlpha = 1f
        }
    }

    val predictionText = prediction?.toString() ?: "—"
    val scoreText = score?.toString() ?: "—"

    // Build content description for accessibility
    val contentDescription = when (state) {
        ScoreCellState.Future -> "Future round — no data entered"
        ScoreCellState.Current -> {
            val predDesc = if (prediction != null) "prediction $prediction" else "no prediction"
            val scoreDesc = if (score != null) "score $score" else "not scored yet"
            "Current round, $predDesc, $scoreDesc"
        }
        ScoreCellState.Completed -> {
            val predDesc = if (prediction != null) "prediction $prediction" else "no prediction"
            val scoreDesc = if (score != null) "score $score" else "no score"
            "Completed round, $predDesc, $scoreDesc"
        }
    }

    Surface(
        modifier = modifier
            .fillMaxWidth(),
            //.semantics {  contentDescription= contentDescription },
        color = backgroundColor,
        shape = RoundedCornerShape(0.dp), // No rounding - cells are flush in grid
        tonalElevation = 0.dp
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(0.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Left section: Prediction (narrow, fixed width ~40dp)
            Box(
                modifier = Modifier
                    .width(40.dp)
                    .fillMaxHeight()
                    .padding(horizontal = 8.dp, vertical = 4.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = predictionText,
                    style = typography.labelMedium.copy(
                        fontWeight = FontWeight.Medium,
                        fontSize = 12.sp
                    ),
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
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(horizontal = 12.dp, vertical = 4.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                Text(
                    text = scoreText,
                    style = typography.bodyLarge.copy(
                        fontWeight = FontWeight.Normal,
                        fontSize = 16.sp
                    ),
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
                score = null,
                state = ScoreCellState.Future,
                modifier = Modifier.width(200.dp).height(48.dp)
            )
            ScoreCell(
                prediction = 2,
                score = null,
                state = ScoreCellState.Future,
                modifier = Modifier.width(200.dp).height(48.dp)
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
                prediction = 2,
                score = null,
                state = ScoreCellState.Current,
                modifier = Modifier.width(200.dp).height(48.dp)
            )
            ScoreCell(
                prediction = 3,
                score = 5,
                state = ScoreCellState.Current,
                modifier = Modifier.width(200.dp).height(48.dp)
            )
            ScoreCell(
                prediction = 0,
                score = null,
                state = ScoreCellState.Current,
                modifier = Modifier.width(200.dp).height(48.dp)
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
                score = 5,
                state = ScoreCellState.Completed,
                modifier = Modifier.width(200.dp).height(48.dp)
            )
            ScoreCell(
                prediction = 3,
                score = 3,
                state = ScoreCellState.Completed,
                modifier = Modifier.width(200.dp).height(48.dp)
            )
            ScoreCell(
                prediction = 0,
                score = 0,
                state = ScoreCellState.Completed,
                modifier = Modifier.width(200.dp).height(48.dp)
            )
            ScoreCell(
                prediction = null,
                score = 10,
                state = ScoreCellState.Completed,
                modifier = Modifier.width(200.dp).height(48.dp)
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
                ScoreCell(prediction = null, score = null, state = ScoreCellState.Future, modifier = Modifier.width(150.dp).height(48.dp))
                ScoreCell(prediction = null, score = null, state = ScoreCellState.Future, modifier = Modifier.width(150.dp).height(48.dp))
                ScoreCell(prediction = null, score = null, state = ScoreCellState.Future, modifier = Modifier.width(150.dp).height(48.dp))
            }

            // Current row (highlighted)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                ScoreCell(prediction = 2, score = null, state = ScoreCellState.Current, modifier = Modifier.width(150.dp).height(48.dp))
                ScoreCell(prediction = 3, score = null, state = ScoreCellState.Current, modifier = Modifier.width(150.dp).height(48.dp))
                ScoreCell(prediction = 1, score = null, state = ScoreCellState.Current, modifier = Modifier.width(150.dp).height(48.dp))
            }

            // Completed rows
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                ScoreCell(prediction = 2, score = 5, state = ScoreCellState.Completed, modifier = Modifier.width(150.dp).height(48.dp))
                ScoreCell(prediction = 3, score = 3, state = ScoreCellState.Completed, modifier = Modifier.width(150.dp).height(48.dp))
                ScoreCell(prediction = 1, score = 0, state = ScoreCellState.Completed, modifier = Modifier.width(150.dp).height(48.dp))
            }
        }
    }
}