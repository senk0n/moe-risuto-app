import dev.senk0n.moerisuto.App
import org.jetbrains.skiko.wasm.onWasmReady

fun main() {
    onWasmReady {
        BrowserViewportWindow("Moe Risuto") {
            App()
        }
    }
}
