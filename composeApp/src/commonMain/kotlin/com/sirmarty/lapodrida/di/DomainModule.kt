package com.sirmarty.lapodrida.di

import com.sirmarty.lapodrida.domain.usecase.CreateGameUseCase
import com.sirmarty.lapodrida.domain.usecase.GetCurrentGameUseCase
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val domainModule = module {
    factoryOf(::CreateGameUseCase)
    factoryOf(::GetCurrentGameUseCase)
}