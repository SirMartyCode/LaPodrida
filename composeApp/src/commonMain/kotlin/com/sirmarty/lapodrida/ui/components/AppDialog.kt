package com.sirmarty.lapodrida.ui.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.sirmarty.lapodrida.ui.theme.LaPodridaTheme

@Composable
fun AppDialog(
    title: String,
    text: String,
    confirmText: String,
    onConfirm: () -> Unit,
    modifier: Modifier = Modifier,
    dismissText: String? = null,
    onDismiss: (() -> Unit)? = null,
    icon: @Composable (() -> Unit)? = null,
) {
    AlertDialog(
        onDismissRequest = { onDismiss?.invoke() },
        modifier = modifier,
        icon = icon,
        title = {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Center,
            )
        },
        text = {
            Text(
                text = text,
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
            )
        },
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text(
                    text = confirmText,
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.secondary,
                )
            }
        },
        dismissButton = if (dismissText != null && onDismiss != null) {
            {
                TextButton(onClick = onDismiss) {
                    Text(
                        text = dismissText,
                        style = MaterialTheme.typography.labelLarge,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }
            }
        } else null,
        containerColor = MaterialTheme.colorScheme.surfaceContainer,
        titleContentColor = MaterialTheme.colorScheme.onSurface,
        textContentColor = MaterialTheme.colorScheme.onSurfaceVariant,
        shape = MaterialTheme.shapes.large,
    )
}

@Preview
@Composable
private fun AppDialogPreview() {
    LaPodridaTheme {
        AppDialog(
            title = "Delete game?",
            text = "This action cannot be undone.",
            confirmText = "Delete",
            onConfirm = {},
            dismissText = "Cancel",
            onDismiss = {},
        )
    }
}
