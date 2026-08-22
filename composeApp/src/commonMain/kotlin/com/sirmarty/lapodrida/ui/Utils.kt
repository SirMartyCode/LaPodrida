package com.sirmarty.lapodrida.ui

import androidx.compose.ui.Modifier

fun Modifier.onlyIf(condition: Boolean, modifier: Modifier.() -> Modifier): Modifier {
    return if (condition) {
        then(modifier(Modifier))
    } else {
        this
    }
}