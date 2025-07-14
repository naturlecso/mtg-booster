package feature.home

import domain.SelectedCardSet

data class HomeViewState(
    val selectedCardSet: SelectedCardSet = SelectedCardSet()
)
