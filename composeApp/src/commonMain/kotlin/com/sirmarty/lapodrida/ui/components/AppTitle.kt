package com.sirmarty.lapodrida.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.sirmarty.lapodrida.ui.theme.LaPodridaTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun AppTitle(
    text: String,
    modifier: Modifier = Modifier,
) {
    Text(
        text = text,
        modifier = modifier,
        style = MaterialTheme.typography.displayLarge,
        color = MaterialTheme.colorScheme.secondary,
        textAlign = TextAlign.Center,
    )
}

@Preview
@Composable
private fun AppTitlePreview() {
    LaPodridaTheme {
        Column(Modifier.padding(16.dp)) {
            AppTitle(text = "La Podrida")
        }
    }
}
