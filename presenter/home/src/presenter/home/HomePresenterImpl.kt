package presenter.home

import base.extensions.coroutineScope
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import domain.repository.CardSetRepository
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
            cardSetRepository.fetch() // TODO relocate to app init
            cardSetRepository.observeSelectedCardSet()
                .collect { viewState.value = viewState.value.copy(selectedCardSet = it) }
        }
    }

    override fun onChooseSet() = onNavigateToSetChoose()

    override fun onChangeSetImage() {
        coroutineScope.launch {
            cardSetRepository.shuffleCardImageOnSelectedCardSet()
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
