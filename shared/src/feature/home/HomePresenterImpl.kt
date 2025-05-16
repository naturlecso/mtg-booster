package feature.home

import com.arkivanov.decompose.ComponentContext
import me.tatarka.inject.annotations.Assisted
import me.tatarka.inject.annotations.Inject

@Inject
class HomePresenterFactory(
    val create: (
        componentContext: ComponentContext
    ) -> HomePresenterImpl
)

@Inject
class HomePresenterImpl(
    @Assisted componentContext: ComponentContext
): HomePresenter, ComponentContext by componentContext
