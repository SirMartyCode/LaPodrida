package com.sirmarty.lapodrida.di

import com.sirmarty.lapodrida.domain.usecase.NewGameUseCase
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val domainModule = module {
    factoryOf(::NewGameUseCase)
}