package com.sirmarty.lapodrida.data.service

import java.util.Locale

actual fun LocalizationService.changeLang(lang: String) {
    val locale = Locale(lang)
    Locale.setDefault(locale)
}