package com.sirmarty.lapodrida.ui.navigation

sealed class Routes(val route:String) {
    data object Menu: Routes("menu")
    data object GameSettings: Routes("gameSettings")
    data object Game: Routes("game")
}
