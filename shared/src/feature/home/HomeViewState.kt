package feature.home

import domain.model.SelectedCardSet

data class HomeViewState(
    val selectedCardSet: SelectedCardSet = SelectedCardSet()
)
