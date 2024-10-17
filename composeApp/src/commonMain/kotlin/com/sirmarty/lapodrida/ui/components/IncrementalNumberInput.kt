package com.sirmarty.lapodrida.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Remove
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun IncrementalNumberInput(
    value: Int,
    incrementEnabled: Boolean,
    decrementEnabled: Boolean,
    onValueUpdated: (Int) -> Unit,
) {
    Row(
        modifier = Modifier.background(Color.White),
        verticalAlignment = Alignment.CenterVertically
    ) {
        FilledIconButton(
            onClick = { onValueUpdated(value - 1) },
            enabled = decrementEnabled
        ) {
            Icon(imageVector = Icons.Rounded.Remove, contentDescription = "")
        }
        Text(
            text = value.toString(),
            modifier = Modifier.width(24.dp),
            textAlign = TextAlign.Center,
            maxLines = 1
        )
        FilledIconButton(
            onClick = { onValueUpdated(value + 1) },
            enabled = incrementEnabled
        ) {
            Icon(imageVector = Icons.Rounded.Add, contentDescription = "")
        }
    }
}