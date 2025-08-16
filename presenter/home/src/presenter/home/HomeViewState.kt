package presenter.home

import domain.model.SelectedCardSet

data class HomeViewState(
    val selectedCardSet: SelectedCardSet? = null,
    val loading: Boolean = false,
    val error: String? = null
)
