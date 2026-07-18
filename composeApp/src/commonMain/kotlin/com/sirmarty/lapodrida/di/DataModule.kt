package com.sirmarty.lapodrida.di

import com.sirmarty.lapodrida.data.repository.GameDataRepository
import com.sirmarty.lapodrida.data.repository.LanguageDataRepository
import com.sirmarty.lapodrida.data.service.LocalizationService
import com.sirmarty.lapodrida.domain.repository.GameRepository
import com.sirmarty.lapodrida.domain.repository.LanguageRepository
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val dataModule = module {
    factory<GameRepository> { GameDataRepository(get()) }
    factory<LanguageRepository> { LanguageDataRepository(get()) }

    factoryOf(::LocalizationService)
}