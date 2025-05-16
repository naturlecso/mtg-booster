import androidx.compose.ui.window.ComposeUIViewController
import platform.UIKit.UIViewController
import root.RootPresenter

fun rootViewController(root: RootPresenter): UIViewController =
    ComposeUIViewController {
        // Render the UI here
        Screen(
            presenter = root
        )
    }
