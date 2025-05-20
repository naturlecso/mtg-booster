import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import root.RootPresenter
import root.RootScreen
import ui.MtgTypography

@Composable
fun Screen(
    presenter: RootPresenter,
    modifier: Modifier = Modifier
) {
    MaterialTheme(
        typography = MtgTypography()
    ) {
        Surface(
            modifier = modifier
                .fillMaxSize(),
        ) {
            RootScreen(
                presenter = presenter,
                modifier = Modifier
                    .fillMaxSize()
            )
        }
    }
}
