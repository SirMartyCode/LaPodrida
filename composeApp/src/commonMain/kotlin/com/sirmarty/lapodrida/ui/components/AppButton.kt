package com.sirmarty.lapodrida.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.sirmarty.lapodrida.ui.theme.LaPodridaTheme
import org.jetbrains.compose.ui.tooling.preview.Preview


/**
 * Primary call-to-action button. Filled, high emphasis.
 */
@Composable
fun AppPrimaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    loading: Boolean = false,
    icon: ImageVector? = null,
) {
    BaseButton(
        text = text,
        onClick = onClick,
        modifier = modifier.fillMaxWidth(),
        enabled = enabled,
        loading = loading,
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.secondary,
            contentColor = MaterialTheme.colorScheme.primary,
            disabledContainerColor = MaterialTheme.colorScheme.surfaceVariant,
            disabledContentColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f),
        ),
        leadingContent = {
            icon?.let {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    modifier = Modifier.size(24.dp),
                )
            }
        }
    )
}

/**
 * Secondary button. Outlined with a gold accent border, medium emphasis.
 */
@Composable
fun AppSecondaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    loading: Boolean = false,
    icon: ImageVector? = null,
) {
    val iconColor =
        if (enabled) MaterialTheme.colorScheme.secondary
        else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f)

    val borderColor =
        if (enabled) MaterialTheme.colorScheme.secondary
        else MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.3f)


    BaseButton(
        text = text,
        onClick = onClick,
        modifier = modifier.fillMaxWidth(),
        enabled = enabled,
        loading = loading,
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface,
            disabledContainerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.5f),
            disabledContentColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f),
        ),
        border = BorderStroke(width = 1.dp, color = borderColor),
        leadingContent = icon?.let {
            {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    modifier = Modifier.size(24.dp),
                    tint = iconColor,
                )
            }
        }
    )
}

/**
 * Ghost button. No fill, no border — lowest emphasis. Gold text/icon.
 */
@Composable
fun AppGhostButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    loading: Boolean = false,
    icon: ImageVector? = null,
) {
    val iconColor =
        if (enabled) MaterialTheme.colorScheme.secondary
        else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f)

    BaseButton(
        text = text,
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        loading = loading,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent,
            contentColor = MaterialTheme.colorScheme.secondary,
            disabledContainerColor = Color.Transparent,
            disabledContentColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f),
        ),
        leadingContent = icon?.let {
            {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    modifier = Modifier.size(24.dp),
                    tint = iconColor
                )
            }
        }
    )
}

@Composable
internal fun BaseButton(
    text: String,
    onClick: () -> Unit,
    colors: ButtonColors,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    loading: Boolean = false,
    border: BorderStroke? = null,
    shape: Shape = MaterialTheme.shapes.large,
    contentPadding: PaddingValues = PaddingValues(horizontal = 24.dp, vertical = 20.dp),
    textStyle: TextStyle = MaterialTheme.typography.titleMedium,
    leadingContent: @Composable (RowScope.() -> Unit)? = null,
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        shape = shape,
        colors = colors,
        border = border,
        contentPadding = contentPadding,
    ) {
        if (loading) {
            CircularProgressIndicator(
                modifier = Modifier.size(24.dp),
                color = MaterialTheme.colorScheme.secondary,
                strokeWidth = 2.dp,
            )
        } else {
            leadingContent?.let {
                leadingContent()
                Spacer(Modifier.width(8.dp))
            }
            Text(text = text, style = textStyle)
        }
    }
}

@Preview
@Composable
private fun AppPrimaryButtonPreview() {
    LaPodridaTheme {
        Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            AppPrimaryButton(text = "Primary Button", onClick = {})
            AppPrimaryButton(text = "With Icon", onClick = {}, icon = Icons.Rounded.Add)
            AppPrimaryButton(text = "Disabled", onClick = {}, enabled = false)
            AppPrimaryButton(
                text = "Disabled + Icon",
                onClick = {},
                enabled = false,
                icon = Icons.Rounded.Add
            )
        }
    }
}

@Preview
@Composable
private fun AppSecondaryButtonPreview() {
    LaPodridaTheme {
        Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            AppSecondaryButton(text = "Secondary Button", onClick = {})
            AppSecondaryButton(text = "With Icon", onClick = {}, icon = Icons.Rounded.Add)
            AppSecondaryButton(text = "Disabled", onClick = {}, enabled = false)
            AppSecondaryButton(
                text = "Disabled + Icon",
                onClick = {},
                enabled = false,
                icon = Icons.Rounded.Add
            )
        }
    }
}

@Preview
@Composable
private fun AppGhostButtonPreview() {
    LaPodridaTheme {
        Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            AppGhostButton(text = "Ghost Button", onClick = {})
            AppGhostButton(text = "With Icon", onClick = {}, icon = Icons.Rounded.Add)
            AppGhostButton(text = "Disabled", onClick = {}, enabled = false)
            AppGhostButton(
                text = "Disabled + Icon",
                onClick = {},
                enabled = false,
                icon = Icons.Rounded.Add
            )
        }
    }
}
