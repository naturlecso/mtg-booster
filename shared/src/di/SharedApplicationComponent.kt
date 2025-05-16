package di

import root.RootPresenterFactory
import software.amazon.lastmile.kotlin.inject.anvil.ContributesTo
import software.amazon.lastmile.kotlin.inject.anvil.SingleIn

@ContributesTo(AppScope::class)
@SingleIn(AppScope::class)
interface SharedApplicationComponent {
    val rootPresenterFactory: RootPresenterFactory
}
