package com.sirmarty.lapodrida.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.sirmarty.lapodrida.ui.theme.LaPodridaTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

/**
 * Themed boolean toggle switch wrapping Material 3 [Switch] with card-game token styling.
 *
 * Checked state uses gold (secondary) track with dark (onSecondary) thumb.
 * Unchecked state uses muted surfaceVariant track with onSurfaceVariant thumb.
 *
 * @param checked Current checked state
 * @param onCheckedChange Callback when checked state changes
 * @param modifier Modifier for layout
 * @param enabled Whether the switch is interactive
 * @param contentDescription Optional accessibility description for the switch setting
 */
@Composable
fun AppSwitch(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    contentDescription: String? = null,
) {
    val colors = SwitchDefaults.colors(
        checkedTrackColor = MaterialTheme.colorScheme.secondary,
        checkedThumbColor = MaterialTheme.colorScheme.onSecondary,
        uncheckedTrackColor = MaterialTheme.colorScheme.surfaceVariant,
        uncheckedThumbColor = MaterialTheme.colorScheme.onSurfaceVariant,
        disabledCheckedTrackColor = MaterialTheme.colorScheme.secondary.copy(alpha = 0.38f),
        disabledCheckedThumbColor = MaterialTheme.colorScheme.onSecondary.copy(alpha = 0.38f),
        disabledUncheckedTrackColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.38f),
        disabledUncheckedThumbColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.38f),
    )

    val semanticsModifier = if (contentDescription != null) {
        modifier.semantics { this.contentDescription = contentDescription }
    } else {
        modifier
    }

    Switch(
        checked = checked,
        onCheckedChange = onCheckedChange,
        modifier = semanticsModifier
            .padding(vertical = 8.dp)
            .height(48.dp),
        enabled = enabled,
        colors = colors,
    )
}

@Preview
@Composable
private fun AppSwitchPreview() {
    LaPodridaTheme {
        Column(
            Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AppSwitch(
                checked = false,
                onCheckedChange = {},
            )
            AppSwitch(
                checked = true,
                onCheckedChange = { },
            )
            AppSwitch(
                checked = false,
                onCheckedChange = { },
                enabled = false,
            )
            AppSwitch(
                checked = true,
                onCheckedChange = { },
                enabled = false,
            )
        }
    }
}