package presenter.cardsetchooser

import base.extensions.coroutineScope
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import domain.repository.CardSetRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import me.tatarka.inject.annotations.Assisted
import me.tatarka.inject.annotations.Inject
import software.amazon.lastmile.kotlin.inject.anvil.AppScope
import software.amazon.lastmile.kotlin.inject.anvil.ContributesBinding

@Inject
class CardSetChooserPresenterImpl(
    @Assisted componentContext: ComponentContext,
    @Assisted private val onSetSelectionCompleted: () -> Unit,
    private val cardSetRepository: CardSetRepository
): CardSetChooserPresenter, ComponentContext by componentContext {
    override val viewState = MutableValue(CardSetChooserViewModel())
    private val coroutineScope = coroutineScope()

    init {
        coroutineScope.launch {
            cardSetRepository.observeCardSets()
                .first()
                .let { viewState.value = viewState.value.copy(cardSetList = it) }
        }
    }

    override fun onSelectCardSet(code: String) {
        coroutineScope.launch {
            cardSetRepository.selectCardSet(code)
            onSetSelectionCompleted()
        }
    }
}

@Inject
@ContributesBinding(AppScope::class, CardSetChooserPresenter.Factory::class)
class CardSetChooserPresenterFactoryImpl(
    val create: (
        componentContext: ComponentContext,
        onSetSelectionCompleted: () -> Unit
    ) -> CardSetChooserPresenterImpl
) : CardSetChooserPresenter.Factory {
    override fun invoke(
        componentContext: ComponentContext,
        onSetSelectionCompleted: () -> Unit
    ): CardSetChooserPresenter = create(componentContext, onSetSelectionCompleted)
}
