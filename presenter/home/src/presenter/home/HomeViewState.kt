package presenter.home

import domain.model.SelectedCardSet

data class HomeViewState(
    val selectedCardSet: SelectedCardSet = SelectedCardSet() // TODO set nullable
)
