package com.sirmarty.lapodrida.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Remove
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.sirmarty.lapodrida.ui.theme.LaPodridaTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

/**
 * Numeric stepper for bounded integer input (e.g. player count).
 *
 * Composes [AppIconButton] for increment/decrement controls and a themed [Text]
 * display for the current value.
 */
@Composable
fun AppIncrementalNumberInput(
    value: Int,
    incrementEnabled: Boolean,
    decrementEnabled: Boolean,
    onValueUpdated: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    AppIncrementalNumberInput(
        value = value,
        incrementEnabled = incrementEnabled,
        decrementEnabled = decrementEnabled,
        onValueIncreased = { onValueUpdated(value + 1) },
        onValueDecreased = { onValueUpdated(value - 1) },
        modifier = modifier,
    )
}

/**
 * Numeric stepper for bounded integer input with explicit increment/decrement callbacks.
 */
@Composable
fun AppIncrementalNumberInput(
    value: Int,
    incrementEnabled: Boolean,
    decrementEnabled: Boolean,
    onValueIncreased: () -> Unit,
    onValueDecreased: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .background(
                color = MaterialTheme.colorScheme.surfaceVariant,
                shape = MaterialTheme.shapes.small,
            )
            .height(48.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        AppIconButton(
            icon = Icons.Rounded.Remove,
            contentDescription = "Decrement",
            onClick = onValueDecreased,
            enabled = decrementEnabled,
        )
        Text(
            text = value.toString(),
            modifier = Modifier.width(48.dp),
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center,
            maxLines = 1,
        )
        AppIconButton(
            icon = Icons.Rounded.Add,
            contentDescription = "Increment",
            onClick = onValueIncreased,
            enabled = incrementEnabled,
        )
    }
}

@Preview
@Composable
private fun AppIncrementalNumberInputPreview() {
    LaPodridaTheme {
        Column(
            Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            var value1 by mutableStateOf(2)
            AppIncrementalNumberInput(
                value = value1,
                incrementEnabled = true,
                decrementEnabled = true,
                onValueUpdated = { value1 = it },
            )

            var value2 by mutableStateOf(4)
            AppIncrementalNumberInput(
                value = value2,
                incrementEnabled = true,
                decrementEnabled = true,
                onValueIncreased = { value2++ },
                onValueDecreased = { value2-- },
            )

            var value3 by mutableStateOf(2)
            AppIncrementalNumberInput(
                value = value3,
                incrementEnabled = false,
                decrementEnabled = false,
                onValueUpdated = { value3 = it },
            )
        }
    }
}