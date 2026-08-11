package com.sirmarty.lapodrida

import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.sirmarty.lapodrida.ui.navigation.MainNavHost
import com.sirmarty.lapodrida.ui.theme.LaPodridaTheme

@Composable
fun App() {
    LaPodridaTheme {
        Scaffold(
            modifier = Modifier.systemBarsPadding()
        ) { paddingValues ->
            MainNavHost(paddingValues)
        }
    }
}