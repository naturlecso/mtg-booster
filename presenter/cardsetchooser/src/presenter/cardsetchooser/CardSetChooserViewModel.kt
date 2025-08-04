package presenter.cardsetchooser

import domain.model.CardSet

data class CardSetChooserViewModel(
    val cardSetList: List<CardSet> = emptyList()
)
