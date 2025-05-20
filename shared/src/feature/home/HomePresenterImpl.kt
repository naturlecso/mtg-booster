package feature.home

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import me.tatarka.inject.annotations.Assisted
import me.tatarka.inject.annotations.Inject

@Inject
class HomePresenterFactory(
    val create: (
        componentContext: ComponentContext
    ) -> HomePresenterImpl
)

@Inject
class HomePresenterImpl(
    @Assisted componentContext: ComponentContext
): HomePresenter, ComponentContext by componentContext {
    override val viewState = MutableValue(HomeViewState())

    override fun onChooseSet() {
        // navigate to set selection screen
    }

    override fun onChangeSetImage() {
        // change set image
    }

    override fun onGenerateBooster() {
        // navigate to booster generator
    }
}
