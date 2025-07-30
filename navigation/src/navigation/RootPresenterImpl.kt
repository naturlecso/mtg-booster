package navigation

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.value.Value
import presenter.home.HomePresenter
import presenter.home.HomePresenterFactory
import me.tatarka.inject.annotations.Assisted
import me.tatarka.inject.annotations.Inject
import navigation.RootDestinationConfig.Home

@Inject
class RootPresenterFactory(
    val create: (
        componentContext: ComponentContext
    ) -> RootPresenterImpl
)

@Inject
class RootPresenterImpl(
    private val homePresenterFactory: HomePresenterFactory,
    @Assisted componentContext: ComponentContext
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
        context: ComponentContext
    ): RootPresenter.Child =
        when (config) {
            is Home -> RootPresenter.Child.HomeChild(homePresenter(context))
        }

    private fun homePresenter(
        context: ComponentContext
    ): HomePresenter = homePresenterFactory
        .create(context)
}
