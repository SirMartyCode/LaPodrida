package com.sirmarty.lapodrida.di

import com.sirmarty.lapodrida.ui.screens.game.GameViewModel
import com.sirmarty.lapodrida.ui.screens.gamesettings.GameSettingsViewModel
import com.sirmarty.lapodrida.ui.screens.menu.MenuViewModel
import org.koin.compose.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val uiModule = module {
    viewModelOf(::MenuViewModel)
    viewModelOf(::GameSettingsViewModel)
    viewModelOf(::GameViewModel)
}