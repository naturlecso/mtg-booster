package feature.home

import domain.MtgSet

data class HomeViewState(
    val selectedMtgSet: MtgSet = MtgSet()
)
