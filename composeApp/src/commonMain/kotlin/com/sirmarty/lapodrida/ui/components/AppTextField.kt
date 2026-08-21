package com.sirmarty.lapodrida.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sirmarty.lapodrida.ui.theme.LaPodridaTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

/**
 * Themed text input field wrapping Material 3 [TextField] with card-game token styling.
 *
 * Provides consistent container colors, label behavior, focused/error states, and shape
 * across all text input fields in the app.
 *
 * @param value Current text value
 * @param onValueChange Callback when text changes
 * @param modifier Modifier for layout
 * @param label Optional label text (floats when focused or field has value)
 * @param enabled Whether the field is interactive
 * @param singleLine Whether to restrict to a single line
 * @param maxLines Maximum number of lines (ignored if singleLine is true)
 * @param isError Whether to show error styling
 * @param keyboardOptions Keyboard configuration (IME action, etc.)
 * @param keyboardActions Keyboard action callbacks
 */
@Composable
fun AppTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    label: String? = null,
    enabled: Boolean = true,
    singleLine: Boolean = true,
    maxLines: Int = 1,
    isError: Boolean = false,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
) {
    val colors = TextFieldDefaults.colors(
        focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
        unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
        disabledContainerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.38f),
        errorContainerColor = MaterialTheme.colorScheme.surfaceVariant,
        focusedTextColor = MaterialTheme.colorScheme.onSurface,
        unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
        disabledTextColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f),
        errorTextColor = MaterialTheme.colorScheme.error,
        focusedLabelColor = MaterialTheme.colorScheme.secondary,
        unfocusedLabelColor = MaterialTheme.colorScheme.onSurfaceVariant,
        disabledLabelColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.38f),
        errorLabelColor = MaterialTheme.colorScheme.error,
        cursorColor = MaterialTheme.colorScheme.secondary,
        errorCursorColor = MaterialTheme.colorScheme.error,
        focusedIndicatorColor = MaterialTheme.colorScheme.secondary,
        unfocusedIndicatorColor = MaterialTheme.colorScheme.outlineVariant,
        disabledIndicatorColor = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.38f),
        errorIndicatorColor = MaterialTheme.colorScheme.error,
    )

    TextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier
            .fillMaxWidth()
            .height(48.dp),
        label = { label?.let { Text(text = it, style = MaterialTheme.typography.labelMedium) } },
        enabled = enabled,
        singleLine = singleLine,
        maxLines = maxLines,
        isError = isError,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        colors = colors,
        shape = MaterialTheme.shapes.medium,
        textStyle = MaterialTheme.typography.bodyLarge,
    )
}

@Preview
@Composable
private fun AppTextFieldPreview() {
    LaPodridaTheme {
        Column(
            Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            var text1 by mutableStateOf("")
            var text2 by mutableStateOf("Player 1")
            var text3 by mutableStateOf("")

            AppTextField(
                value = text1,
                onValueChange = { text1 = it },
                label = "Player name",
            )
            AppTextField(
                value = text2,
                onValueChange = { text2 = it },
                label = "Player name",
            )
            AppTextField(
                value = text3,
                onValueChange = { text3 = it },
                label = "Player name",
                isError = true,
            )
            AppTextField(
                value = "",
                onValueChange = {},
                label = "Disabled",
                enabled = false,
            )
        }
    }
}