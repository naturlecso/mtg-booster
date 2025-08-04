package navigation

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import presenter.cardsetchooser.CardSetChooserPresenter
import presenter.home.HomePresenter

interface RootPresenter {
    val childStack: Value<ChildStack<*, Child>>

    sealed interface Child {
        class Home(val presenter: HomePresenter): Child
        class CardSetChooser(val presenter: CardSetChooserPresenter): Child
    }
}
