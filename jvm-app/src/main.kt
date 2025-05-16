import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.decompose.extensions.compose.lifecycle.LifecycleController
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import di.DesktopApplicationComponent
import di.create

fun main() = application {
    val lifecycle = LifecycleRegistry()

    val windowState = rememberWindowState()
    LifecycleController(lifecycle, windowState)

    val applicationComponent = DesktopApplicationComponent::class.create()
    val componentContext = DefaultComponentContext(lifecycle = lifecycle)
    val root = applicationComponent
        .rootPresenterFactory
        .create(componentContext)

    Window(
        onCloseRequest = ::exitApplication,
        state = windowState,
        title = "MTG Booster"
    ) {
        Screen(
            presenter = root
        )
    }
}
