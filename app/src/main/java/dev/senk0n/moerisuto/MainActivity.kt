package dev.senk0n.moerisuto

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.style.TextDirection.Companion.Content
import androidx.compose.ui.tooling.preview.Preview
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.decompose.defaultComponentContext
import com.arkivanov.essenty.lifecycle.Lifecycle
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import dev.senk0n.moerisuto.core.compose.ComposeFactoryDI
import dev.senk0n.moerisuto.core.compose.create
import dev.senk0n.moerisuto.core.navigation.ComponentFactoryDI
import dev.senk0n.moerisuto.core.navigation.create
import dev.senk0n.moerisuto.feature.mylist.MyListConfig
import dev.senk0n.moerisuto.root.RootComponentImpl
import dev.senk0n.moerisuto.root.RootContent
import dev.senk0n.moerisuto.ui.theme.MoeRisutoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val root = RootComponentImpl(defaultComponentContext())
        setContent {
            MoeRisutoTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    RootContent(component = root, modifier = Modifier.fillMaxSize())
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MoeRisutoTheme {
        Greeting("Android")
    }
}