package presenter.home

import base.extensions.coroutineScope
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import domain.repository.CardSetRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import me.tatarka.inject.annotations.Assisted
import me.tatarka.inject.annotations.Inject
import software.amazon.lastmile.kotlin.inject.anvil.AppScope
import software.amazon.lastmile.kotlin.inject.anvil.ContributesBinding

@Inject
class HomePresenterImpl(
    @Assisted componentContext: ComponentContext,
    @Assisted private val onNavigateToSetChoose: () -> Unit,
    private val cardSetRepository: CardSetRepository,
): HomePresenter, ComponentContext by componentContext {
    private val coroutineScope = coroutineScope()
    override val viewState = MutableValue(HomeViewState())

    init {
        coroutineScope.launch {
            cardSetRepository
                .observeSelectedCardSet()
                .onStart { viewState.value = viewState.value.copy(loading = true) }
                .map { selectedCardSet -> HomeViewState(
                    selectedCardSet = selectedCardSet,
                    loading = false
                ) }
                .collect { viewState.value = it }
        }
    }

    override fun onChooseSet() = onNavigateToSetChoose()

    override fun onChangeSetImage() {
        coroutineScope.launch {
            viewState.value = viewState.value.copy(loading = true)
            delay(1000)
            cardSetRepository.shuffleCardImageOnSelectedCardSet()
            viewState.value = viewState.value.copy(loading = false)
        }
    }

    override fun onGenerateBooster() {
        // navigate to booster generator
    }
}

@Inject
@ContributesBinding(AppScope::class, HomePresenter.Factory::class)
class HomePresenterFactoryImpl(
    val create: (
        componentContext: ComponentContext,
        onNavigateToChooseSet: () -> Unit
    ) -> HomePresenterImpl
) : HomePresenter.Factory {
    override fun invoke(
        componentContext: ComponentContext,
        onNavigateToChooseSet: () -> Unit
    ): HomePresenter = create(componentContext, onNavigateToChooseSet)
}
