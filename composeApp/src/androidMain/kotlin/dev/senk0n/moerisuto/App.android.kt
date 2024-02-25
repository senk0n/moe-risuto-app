package dev.senk0n.moerisuto

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.retainedComponent
import dev.senk0n.moerisuto.root.RootComponentImpl
import dev.senk0n.moerisuto.root.RootContent
import dev.senk0n.moerisuto.ui.theme.MoeRisutoTheme

class AppActivity : ComponentActivity() {
    @OptIn(ExperimentalDecomposeApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        val root = retainedComponent { RootComponentImpl(it) }
        setContent {
            MoeRisutoTheme {
                RootContent(component = root, modifier = Modifier.fillMaxSize())
            }
        }
    }
}