package presenter.home

import com.arkivanov.decompose.value.Value

interface HomePresenter {
    val viewState: Value<HomeViewState>

    fun onChooseSet()
    fun onChangeSetImage()
    fun onGenerateBooster()
}
