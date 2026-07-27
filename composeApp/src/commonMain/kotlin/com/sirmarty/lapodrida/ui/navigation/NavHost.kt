package com.sirmarty.lapodrida.ui.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import androidx.savedstate.serialization.SavedStateConfiguration
import com.sirmarty.lapodrida.ui.screens.game.GameScreen
import com.sirmarty.lapodrida.ui.screens.gamesettings.GameSettingsScreen
import com.sirmarty.lapodrida.ui.screens.menu.MenuScreen
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import org.koin.compose.koinInject

@Composable
fun MainNavHost(paddingValues: PaddingValues) {
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

    val navigator: Nav3Navigator = koinInject()
    remember(backStack) { navigator.bind(backStack) }

    NavDisplay(
        modifier = Modifier.padding(paddingValues),
        backStack = backStack,
        onBack = { navigator.goBack() },
        entryDecorators = listOf(
            rememberSaveableStateHolderNavEntryDecorator(),
            rememberViewModelStoreNavEntryDecorator(),
            OpaqueBackgroundNavEntryDecorator(MaterialTheme.colorScheme.surface)
        ),
        entryProvider = entryProvider {
            entry<Route.Menu> { MenuScreen() }
            entry<Route.GameSettings> { GameSettingsScreen() }
            entry<Route.Game> { GameScreen() }
        },
        transitionSpec = { slideInFromRight() },
        popTransitionSpec = { slideOutFromLeft() },
        predictivePopTransitionSpec = { slideOutFromLeft() },
    )
}

private fun slideInFromRight() =
    slideInHorizontally(initialOffsetX = { it }) togetherWith ExitTransition.None

private fun slideOutFromLeft() =
    EnterTransition.None togetherWith slideOutHorizontally(targetOffsetX = { it })