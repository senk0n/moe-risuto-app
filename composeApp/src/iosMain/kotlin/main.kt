import androidx.compose.ui.Modifier
import androidx.compose.ui.window.ComposeUIViewController
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import com.arkivanov.essenty.statekeeper.StateKeeperDispatcher
import dev.senk0n.moerisuto.root.RootComponentImpl
import dev.senk0n.moerisuto.root.RootContent
import platform.UIKit.UIViewController

fun MainViewController(): UIViewController {
    val lifecycle = LifecycleRegistry()
    val stateKeeper = StateKeeperDispatcher()
    val root = RootComponentImpl(
        componentContext = DefaultComponentContext(
            lifecycle = lifecycle,
            stateKeeper = stateKeeper,
        )
    )
    return ComposeUIViewController {
        RootContent(component = root, modifier = Modifier)
    }
}
