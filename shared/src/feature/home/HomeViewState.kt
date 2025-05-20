package feature.home

import domain.CardSet

data class HomeViewState(
    val selectedCardSet: CardSet = CardSet()
)
