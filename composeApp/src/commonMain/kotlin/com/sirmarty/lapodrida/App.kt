package com.sirmarty.lapodrida

import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.sirmarty.lapodrida.ui.navigation.MainNavHost

@Composable
fun App() {
    MaterialTheme {
        Scaffold(
            modifier = Modifier.systemBarsPadding()
        ) { paddingValues ->
            MainNavHost(paddingValues)
        }
    }
}