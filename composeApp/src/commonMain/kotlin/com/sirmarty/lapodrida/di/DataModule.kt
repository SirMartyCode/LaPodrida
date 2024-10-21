package com.sirmarty.lapodrida.di

import com.sirmarty.lapodrida.data.repository.GameSettingsDataRepository
import com.sirmarty.lapodrida.domain.repository.GameSettingsRepository
import org.koin.dsl.module

val dataModule = module {
    factory<GameSettingsRepository> { GameSettingsDataRepository(get()) }
}