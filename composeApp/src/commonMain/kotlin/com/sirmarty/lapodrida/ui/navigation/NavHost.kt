package com.sirmarty.lapodrida.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import androidx.savedstate.serialization.SavedStateConfiguration
import com.sirmarty.lapodrida.ui.screens.game.GameScreen
import com.sirmarty.lapodrida.ui.screens.gamesettings.GameSettingsScreen
import com.sirmarty.lapodrida.ui.screens.menu.MenuScreen
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic

@Composable
fun MainNavHost() {
    // Serialization configuration required for multiplatform (iOS).
    // Explicity register each NavKey subclass so it works on non-JVM targets.
    val config = remember {
        SavedStateConfiguration {
            serializersModule = SerializersModule {
                polymorphic(NavKey::class) {
                    subclass(Route.Menu::class, Route.Menu.serializer())
                    subclass(Route.GameSettings::class, Route.GameSettings.serializer())
                    subclass(Route.Game::class, Route.Game.serializer())
                }
            }
        }
    }

    // Developer-owned back stack: an observable SnapshotStateList<NavKey>.
    // It handles state saving/restoration automatically.
    val backStack = rememberNavBackStack(config, Route.Menu)

    // Handle system back gesture (Android back button / iOS swipe back).
    // If NavigationBackHandler is not available in your CMP version, remove this line.
    //NavigationBackHandler(backStack)

    // NavDisplay observes backStack and renders the current destination.
    NavDisplay(
        backStack = backStack,
        onBack = { backStack.removeLastOrNull() },
        modifier = Modifier,
        entryProvider = entryProvider {
            entry<Route.Menu> {
                MenuScreen(
                    onNewGame = { backStack.add(Route.GameSettings) },
                    onContinueGame = { backStack.add(Route.Game) }
                )
            }
            entry<Route.GameSettings> {
                GameSettingsScreen(
                    onStartGame = { backStack.add(Route.Game) }
                )
            }
            entry<Route.Game> {
                GameScreen()
            }
        }
    )
}