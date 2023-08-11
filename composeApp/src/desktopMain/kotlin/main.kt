import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.extensions.compose.jetbrains.lifecycle.LifecycleController
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import com.arkivanov.essenty.statekeeper.StateKeeperDispatcher
import dev.senk0n.moerisuto.runOnUiThread
import dev.senk0n.moerisuto.root.RootComponentImpl
import dev.senk0n.moerisuto.root.RootContent
import dev.senk0n.moerisuto.ui.theme.MoeRisutoTheme

@OptIn(ExperimentalDecomposeApi::class)
fun main() {
    val lifecycle = LifecycleRegistry()
    val stateKeeper = StateKeeperDispatcher()
    val root = runOnUiThread {
        RootComponentImpl(
            componentContext = DefaultComponentContext(
                lifecycle = lifecycle,
                stateKeeper = stateKeeper,
            )
        )
    }

    application {
        val windowState = rememberWindowState()
        LifecycleController(lifecycle, windowState)

        Window(
            title = "Moe Risuto",
            state = windowState,
            onCloseRequest = ::exitApplication,
        ) {
            MoeRisutoTheme {
                RootContent(component = root, modifier = Modifier.fillMaxSize())
            }
        }
    }
}