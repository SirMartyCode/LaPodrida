package com.sirmarty.lapodrida.domain.entities

sealed class Language(val isoFormat: String) {
    data object English : Language("en")
    data object Spanish : Language("es")
    data object Catalan : Language("ca")
}