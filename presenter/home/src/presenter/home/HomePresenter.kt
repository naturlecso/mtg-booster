package presenter.home

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.Value

interface HomePresenter {
    val viewState: Value<HomeViewState>

    fun onChooseSet()
    fun onChangeSetImage()
    fun onGenerateBooster()

    interface Factory {
        operator fun invoke(
            componentContext: ComponentContext,
            onNavigateToChooseSet: () -> Unit
        ): HomePresenter
    }
}
