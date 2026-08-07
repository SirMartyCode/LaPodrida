package com.sirmarty.lapodrida.ui.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation3.runtime.NavEntryDecorator

class OpaqueBackgroundNavEntryDecorator(
    private val color: Color
) : NavEntryDecorator<Any>(
    decorate = { entry ->
        Box(Modifier.fillMaxSize().background(color)) {
            entry.Content()
        }
    }
)