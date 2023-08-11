package dev.senk0n.moerisuto

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import dev.senk0n.moerisuto.root.RootComponentImpl

@Composable
internal fun App() {
    Column(modifier = Modifier.fillMaxSize()) {
        Text("hello from old template")
    }
}
