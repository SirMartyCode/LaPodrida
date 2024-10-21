package com.sirmarty.lapodrida.di

import com.sirmarty.lapodrida.domain.usecase.GetCurrentGameSettingsUseCase
import com.sirmarty.lapodrida.domain.usecase.SaveGameSettingsUseCase
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val domainModule = module {
    factoryOf(::SaveGameSettingsUseCase)
    factoryOf(::GetCurrentGameSettingsUseCase)
}