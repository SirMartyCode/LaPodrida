package com.sirmarty.lapodrida.domain.repository

import com.sirmarty.lapodrida.domain.entities.Language

interface LanguageRepository {
    suspend fun changeLanguage(language: Language)
}