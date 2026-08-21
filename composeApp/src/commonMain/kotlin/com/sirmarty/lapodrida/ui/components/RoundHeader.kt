package com.sirmarty.lapodrida.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
import com.sirmarty.lapodrida.ui.theme.LaPodridaTheme

/**
 * Row header for the scoreboard table showing round number and cards per player.
 *
 * @param roundNumber 1-based round index
 * @param cardsPerPlayer number of cards dealt that round
 * @param isCurrent whether this is the active round (gold accent)
 * @param isIndianRound whether this is the special indian round
 * @param modifier optional modifier
 */
@Composable
fun RoundHeader(
    roundNumber: Int,
    cardsPerPlayer: Int,
    isCurrent: Boolean = false,
    isIndianRound: Boolean = false,
    modifier: Modifier = Modifier
) {
    val colors = MaterialTheme.colorScheme
    val typography = MaterialTheme.typography

    val bgColor = colors.surface
    val roundTextColor = if (isCurrent) colors.secondary else colors.onSurface
    val cardsTextColor = colors.onSurfaceVariant

    val description = buildString {
        append("Ronda $roundNumber, $cardsPerPlayer cartes")
        if (isCurrent) append(", ronda actual")
        if (isIndianRound) append(", ronda índia")
    }

    // Left gold accent for current round
    val startBorder = if (isCurrent) {
        Modifier.border(
            width = 3.dp,
            color = colors.secondary,
            shape = MaterialTheme.shapes.small
        )
    } else Modifier

    Box(
        modifier = modifier
            .width(40.dp)
            .height(48.dp)
            .background(bgColor)
            .then(startBorder)
            .semantics {
                contentDescription = description
                heading()
            }
            .padding(horizontal = 6.dp, vertical = 4.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = if (isIndianRound) "IND" else "R$roundNumber",
                style = MaterialTheme.typography.labelLarge,
                color = roundTextColor,
                textAlign = TextAlign.Center,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = if (isIndianRound) "Índia" else "$cardsPerPlayer",
                style = MaterialTheme.typography.labelSmall,
                color = cardsTextColor,
                textAlign = TextAlign.Center,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

/**
 * Header for the totals row.
 */
@Composable
fun TotalsHeader(
    modifier: Modifier = Modifier
) {
    val colors = MaterialTheme.colorScheme
    Box(
        modifier = modifier
            .width(40.dp)
            .height(48.dp)
            .background(colors.surfaceVariant)
            .padding(horizontal = 6.dp, vertical = 4.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "TOTAL",
            style = MaterialTheme.typography.labelLarge,
            color = colors.secondary,
            textAlign = TextAlign.Center,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Preview(name = "RoundHeader - Normal")
@Composable
private fun RoundHeaderPreview() {
    LaPodridaTheme {
        Row {
            RoundHeader(roundNumber = 1, cardsPerPlayer = 1)
            RoundHeader(roundNumber = 5, cardsPerPlayer = 5)
            RoundHeader(roundNumber = 8, cardsPerPlayer = 4)
        }
    }
}

@Preview(name = "RoundHeader - Current")
@Composable
private fun RoundHeaderCurrentPreview() {
    LaPodridaTheme {
        Row {
            RoundHeader(roundNumber = 4, cardsPerPlayer = 4, isCurrent = true)
            RoundHeader(roundNumber = 7, cardsPerPlayer = 7, isCurrent = true)
        }
    }
}

@Preview(name = "RoundHeader - Indian")
@Composable
private fun RoundHeaderIndianPreview() {
    LaPodridaTheme {
        Row {
            RoundHeader(roundNumber = 14, cardsPerPlayer = 1, isIndianRound = true)
            RoundHeader(roundNumber = 14, cardsPerPlayer = 1, isCurrent = true, isIndianRound = true)
        }
    }
}

@Preview(name = "RoundHeader - Totals")
@Composable
private fun TotalsHeaderPreview() {
    LaPodridaTheme {
        TotalsHeader()
    }
}
