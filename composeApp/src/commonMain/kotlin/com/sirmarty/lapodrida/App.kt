package com.sirmarty.lapodrida

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import com.sirmarty.lapodrida.ui.navigation.MainNavHost
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    MaterialTheme {
        MainNavHost()
    }
}