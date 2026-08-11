package com.sirmarty.lapodrida.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.History
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.sirmarty.lapodrida.ui.theme.LaPodridaTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

/**
 * Menu entry button. Outlined with a gold accent border, an icon and an
 * optional loading state (spinner replaces the icon while loading).
 */
@Composable
fun MenuButton(
    text: String,
    icon: ImageVector,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    loading: Boolean = false,
) {
    val effectiveEnabled = enabled && !loading

    BaseButton(
        text = text,
        onClick = onClick,
        modifier = modifier,
        enabled = effectiveEnabled,
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface,
            disabledContainerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.5f),
            disabledContentColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f),
        ),
        border = BorderStroke(
            width = 1.dp,
            color = if (effectiveEnabled) {
                MaterialTheme.colorScheme.secondary
            } else {
                MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.3f)
            },
        ),
        leadingContent = if (loading) {
            {
                CircularProgressIndicator(
                    modifier = Modifier.size(24.dp),
                    color = MaterialTheme.colorScheme.secondary,
                    strokeWidth = 2.dp,
                )
            }
        } else {
            {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    modifier = Modifier.size(24.dp),
                    tint = if (effectiveEnabled) {
                        MaterialTheme.colorScheme.secondary
                    } else {
                        MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f)
                    },
                )
            }
        },
    )
}

@Preview
@Composable
private fun MenuButtonPreview() {
    LaPodridaTheme {
        Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            MenuButton(text = "New Game", icon = Icons.Rounded.Add, onClick = {})
            MenuButton(text = "Continue", icon = Icons.Rounded.PlayArrow, onClick = {}, enabled = true)
            MenuButton(text = "Continue", icon = Icons.Rounded.PlayArrow, onClick = {}, enabled = false)
            MenuButton(text = "Loading...", icon = Icons.Rounded.History, onClick = {}, loading = true)
        }
    }
}
