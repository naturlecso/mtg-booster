package presenter.cardsetchooser

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.Value

interface CardSetChooserPresenter {
    val viewState: Value<CardSetChooserViewModel>

    fun onSelectCardSet(code: String)

    interface Factory {
        operator fun invoke(
            componentContext: ComponentContext,
            onSetSelectionCompleted: () -> Unit
        ): CardSetChooserPresenter
    }
}
