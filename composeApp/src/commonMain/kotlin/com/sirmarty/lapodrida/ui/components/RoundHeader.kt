package com.sirmarty.lapodrida.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import com.sirmarty.lapodrida.ui.onlyIf
import com.sirmarty.lapodrida.ui.screens.game.RoundState
import com.sirmarty.lapodrida.ui.theme.LaPodridaTheme

@Composable
fun RoundHeader(
    title: String,
    subtitle: String,
    state: RoundState,
    modifier: Modifier = Modifier
) {
    val colors = MaterialTheme.colorScheme

    val (roundColor, cardsColor) = when (state) {
        RoundState.Current -> colors.secondary to colors.onSurfaceVariant
        RoundState.Completed -> colors.onSurface to colors.onSurfaceVariant
        RoundState.Future ->
            colors.onSurface.copy(alpha = 0.5f) to colors.onSurfaceVariant.copy(alpha = 0.5f)
    }

    val borderShape = MaterialTheme.shapes.small

    Box(
        modifier = modifier
            .width(ScoreTableTokens.RowHeaderWidth)
            .height(ScoreTableTokens.CellHeight)
            .background(colors.surface)
            .onlyIf(state.isCurrent) { // Gold accent for current round
                border(
                    width = 3.dp,
                    color = colors.secondary,
                    shape = borderShape
                )
            }
            .padding(4.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.labelLarge,
                color = roundColor,
                textAlign = TextAlign.Center,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = subtitle,
                style = MaterialTheme.typography.labelSmall,
                color = cardsColor,
                textAlign = TextAlign.Center,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Preview(name = "RoundHeader - Future")
@Composable
private fun RoundHeaderFuturePreview() {
    LaPodridaTheme {
        Column {
            RoundHeader(title = "R1", subtitle = "1", state = RoundState.Future)
            RoundHeader(title = "R5", subtitle = "5", state = RoundState.Future)
        }
    }
}

@Preview(name = "RoundHeader - Current")
@Composable
private fun RoundHeaderCurrentPreview() {
    LaPodridaTheme {
        Column {
            RoundHeader(title = "R4", subtitle = "4", state = RoundState.Current)
            RoundHeader(title = "R7", subtitle = "7", state = RoundState.Current)
        }
    }
}

@Preview(name = "RoundHeader - Completed")
@Composable
private fun RoundHeaderCompletedPreview() {
    LaPodridaTheme {
        Column {
            RoundHeader(title = "R2", subtitle = "2", state = RoundState.Completed)
            RoundHeader(title = "R8", subtitle = "4", state = RoundState.Completed)
        }
    }
}

@Preview(name = "RoundHeader - Indian")
@Composable
private fun RoundHeaderIndianPreview() {
    LaPodridaTheme {
        Column {
            RoundHeader(title = "IND", subtitle = "Índia", state = RoundState.Future)
            RoundHeader(title = "IND", subtitle = "Índia", state = RoundState.Current)
        }
    }
}
