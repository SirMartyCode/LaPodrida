package com.sirmarty.lapodrida.data.repository

import com.sirmarty.lapodrida.data.service.LocalizationService
import com.sirmarty.lapodrida.data.service.changeLang
import com.sirmarty.lapodrida.domain.entities.Language
import com.sirmarty.lapodrida.domain.repository.LanguageRepository

class LanguageDataRepository(
    val localizationService: LocalizationService
): LanguageRepository {
    override suspend fun changeLanguage(language: Language) {
        localizationService.changeLang(language.isoFormat)
    }
}