package com.sirmarty.lapodrida.data.service

import platform.Foundation.NSUserDefaults

actual fun LocalizationService.changeLang(lang: String) {
    NSUserDefaults.standardUserDefaults.setObject(arrayListOf(lang),"AppleLanguages")
}