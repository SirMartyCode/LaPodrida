package com.sirmarty.lapodrida.ui.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
sealed interface Route : NavKey {
    @Serializable data object Menu : Route
    @Serializable data object GameSettings : Route
    @Serializable data object Game : Route
}
