package com.sirmarty.lapodrida.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sirmarty.lapodrida.ui.screens.MenuScreen
import com.sirmarty.lapodrida.ui.screens.game.GameScreen
import com.sirmarty.lapodrida.ui.screens.gamesettings.GameSettingsScreen

@Composable
fun MainNavHost() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Routes.Menu.route
    ) {
        composable(route = Routes.Menu.route) {
            MenuScreen(onButtonClick = {
                navController.navigate(
                    Routes.GameSettings.route
                )
            })
        }
        composable(route = Routes.GameSettings.route) {
            GameSettingsScreen(onStartGame = {
                navController.navigate(Routes.Game.route)
            })
        }
        composable(route = Routes.Game.route) { GameScreen() }
    }
}