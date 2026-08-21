package com.sirmarty.lapodrida.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val LaPodridaColorScheme = darkColorScheme(
    background       = Background,
    surface          = Surface,
    surfaceVariant   = SurfaceVariant,
    primary          = Primary,
    onPrimary        = OnPrimary,
    primaryContainer   = PrimaryContainer,
    onPrimaryContainer = OnPrimaryContainer,
    secondary        = Secondary,
    onSecondary      = OnSecondary,
    tertiary         = Tertiary,
    onTertiary       = OnTertiary,
    error            = Error,
    onError          = OnError,
    onBackground     = OnBackground,
    onSurface        = OnSurface,
    onSurfaceVariant = OnSurfaceVariant,
    outline          = Outline,
    outlineVariant   = OutlineVariant,
    surfaceContainer = SurfaceContainer,
    inverseSurface   = InverseSurface,
    inverseOnSurface = InverseOnSurface
)

@Composable
fun LaPodridaTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = LaPodridaColorScheme,
        typography  = LaPodridaTypography,
        shapes      = LaPodridaShapes,
        content     = content
    )
}
