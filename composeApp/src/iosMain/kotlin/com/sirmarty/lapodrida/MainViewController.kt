package com.sirmarty.lapodrida

import androidx.compose.ui.window.ComposeUIViewController
import com.sirmarty.lapodrida.di.initKoin

fun MainViewController() = ComposeUIViewController(configure = { initKoin() }) { App() }