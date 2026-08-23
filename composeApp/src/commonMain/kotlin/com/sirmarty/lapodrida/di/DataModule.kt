package com.sirmarty.lapodrida.di

import com.sirmarty.lapodrida.data.repository.HybridCurrentGameRepository
import com.sirmarty.lapodrida.data.repository.LanguageDataRepository
import com.sirmarty.lapodrida.data.repository.RoomGamesRepository
import com.sirmarty.lapodrida.data.service.LocalizationService
import com.sirmarty.lapodrida.domain.repository.CurrentGameRepository
import com.sirmarty.lapodrida.domain.repository.GamesRepository
import com.sirmarty.lapodrida.domain.repository.LanguageRepository
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val dataModule = module {
    single<CurrentGameRepository> { HybridCurrentGameRepository(get()) }
    single<GamesRepository> { RoomGamesRepository(get()) }
    factory<LanguageRepository> { LanguageDataRepository(get()) }

    factoryOf(::LocalizationService)
}