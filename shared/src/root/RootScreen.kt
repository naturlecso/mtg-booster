package root

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.stack.animation.plus
import com.arkivanov.decompose.extensions.compose.stack.animation.scale
import com.arkivanov.decompose.extensions.compose.stack.animation.stackAnimation
import feature.home.HomeScreen

@Composable
fun RootScreen(
    presenter: RootPresenter,
    modifier: Modifier = Modifier
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
