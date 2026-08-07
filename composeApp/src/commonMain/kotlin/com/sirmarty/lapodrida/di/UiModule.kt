package com.sirmarty.lapodrida.di

import com.sirmarty.lapodrida.ui.navigation.Nav3Navigator
import com.sirmarty.lapodrida.ui.navigation.Navigator
import com.sirmarty.lapodrida.ui.screens.game.GameViewModel
import com.sirmarty.lapodrida.ui.screens.gamesettings.GameSettingsViewModel
import com.sirmarty.lapodrida.ui.screens.menu.MenuViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

val uiModule = module {
    single { Nav3Navigator() } bind Navigator::class
    viewModelOf(::MenuViewModel)
    viewModelOf(::GameSettingsViewModel)
    viewModelOf(::GameViewModel)
}