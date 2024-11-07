package com.sirmarty.lapodrida.di

import com.sirmarty.lapodrida.data.repository.GameDataRepository
import com.sirmarty.lapodrida.domain.repository.GameRepository
import org.koin.dsl.module

val dataModule = module {
    factory<GameRepository> { GameDataRepository(get()) }
}