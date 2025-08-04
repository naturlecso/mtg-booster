package navigation

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.pushNew
import com.arkivanov.decompose.value.Value
import presenter.home.HomePresenter
import me.tatarka.inject.annotations.Assisted
import me.tatarka.inject.annotations.Inject
import navigation.RootDestinationConfig.Home
import navigation.RootDestinationConfig.CardSetChooser
import presenter.cardsetchooser.CardSetChooserPresenter

@Inject
class RootPresenterFactory(
    val create: (
        componentContext: ComponentContext
    ) -> RootPresenterImpl
)

@Inject
class RootPresenterImpl(
    @Assisted componentContext: ComponentContext,
    private val homePresenterFactory: HomePresenter.Factory,
    private val cardSetChooserPresenterFactory: CardSetChooserPresenter.Factory,
) : RootPresenter, ComponentContext by componentContext {
    private val nav = StackNavigation<RootDestinationConfig>()

    override val childStack: Value<ChildStack<*, RootPresenter.Child>> =
        childStack(
            source = nav,
            initialConfiguration = Home,
            serializer = RootDestinationConfig.serializer(),
            handleBackButton = true,
            childFactory = ::createScreen,
        )

    private fun createScreen(
        config: RootDestinationConfig,
        componentContext: ComponentContext
    ): RootPresenter.Child =
        when (config) {
            is Home -> RootPresenter.Child.Home(
                homePresenterFactory(
                    componentContext = componentContext,
                    onNavigateToChooseSet = { nav.pushNew(CardSetChooser) }
                ))
            is CardSetChooser -> RootPresenter.Child.CardSetChooser(
                cardSetChooserPresenterFactory(
                    componentContext = componentContext,
                    onSetSelectionCompleted = { nav.pop() }
                )
            )
        }
}
