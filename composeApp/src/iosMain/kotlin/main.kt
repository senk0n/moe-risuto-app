import androidx.compose.ui.window.ComposeUIViewController
import dev.senk0n.moerisuto.App
import platform.UIKit.UIViewController

fun MainViewController(): UIViewController {
    return ComposeUIViewController { App() }
}
