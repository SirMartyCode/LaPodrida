package com.sirmarty.lapodrida.ui.navigation

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey

interface Navigator {
    fun navigateTo(route: Route)
    fun goBack()
}

class Nav3Navigator : Navigator {
    private var backStack: NavBackStack<NavKey>? = null

    fun bind(backStack: NavBackStack<NavKey>) {
        this.backStack = backStack
    }

    override fun navigateTo(route: Route) {
        backStack?.add(route)
    }

    override fun goBack() {
        backStack?.let {
            if (it.size > 1) it.removeLastOrNull()
        }
    }
}

val LocalNavigator = staticCompositionLocalOf<Navigator> {
    error("No Navigator provided — wrap your NavDisplay in CompositionLocalProvider")
}
