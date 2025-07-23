package feature.home

import base.extensions.coroutineScope
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import data.repository.CardSetRepository
import kotlinx.coroutines.launch
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
    @Assisted componentContext: ComponentContext,
    private val cardSetRepository: CardSetRepository
): HomePresenter, ComponentContext by componentContext {
    private val coroutineScope = coroutineScope()
    override val viewState = MutableValue(HomeViewState())

    init {
        coroutineScope.launch {
            cardSetRepository.fetch() // TODO relocate to app init
            cardSetRepository.observeSelectedCardSet()
                .collect { viewState.value = viewState.value.copy(selectedCardSet = it) }
        }
    }

    override fun onChooseSet() {
        // navigate to set selection screen
    }

    override fun onChangeSetImage() {
        coroutineScope.launch {
            cardSetRepository.shuffleCardImageOnSelectedCardSet()
        }
    }

    override fun onGenerateBooster() {
        // navigate to booster generator
    }
}
