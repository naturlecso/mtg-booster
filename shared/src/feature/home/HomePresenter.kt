package feature.home

import com.arkivanov.decompose.value.Value

interface HomePresenter {
    val viewState: Value<HomeViewState>

    fun onSelectSet()
    fun onGenerateBooster()
}
