import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.stack.animation.plus
import com.arkivanov.decompose.extensions.compose.stack.animation.scale
import com.arkivanov.decompose.extensions.compose.stack.animation.stackAnimation
import navigation.RootPresenter
import presenter.home.HomeScreen
import theme.typography.MtgTypography

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
            Children(
                stack = presenter.childStack,
                modifier = modifier,
                animation = stackAnimation(animator = fade() + scale())
            ) {
                when (val child = it.instance) {
                    is RootPresenter.Child.HomeChild -> HomeScreen(
                        presenter = child.presenter
                    )
                }
            }
        }
    }
}
