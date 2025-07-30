package navigation

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import presenter.home.HomePresenter

interface RootPresenter {
    val childStack: Value<ChildStack<*, Child>>

    sealed class Child {
        class HomeChild(val presenter: HomePresenter): Child()
    }
}
