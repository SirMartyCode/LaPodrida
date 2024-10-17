package com.sirmarty.lapodrida.di

import com.sirmarty.lapodrida.ui.screens.gamesettings.GameSettingsViewModel
import org.koin.compose.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val uiModule = module {
    viewModelOf(::GameSettingsViewModel)
}