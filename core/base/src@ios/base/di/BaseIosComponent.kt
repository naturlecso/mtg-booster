package base.di

import base.model.AppCoroutineDispatchers
import base.model.AppCoroutineScope
import base.scope.NsQueueCoroutineScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import me.tatarka.inject.annotations.Provides
import software.amazon.lastmile.kotlin.inject.anvil.AppScope
import software.amazon.lastmile.kotlin.inject.anvil.ContributesTo

@ContributesTo(AppScope::class)
interface BaseIosComponent {

    @Provides
    fun provideAppCoroutineScope(dispatchers: AppCoroutineDispatchers): AppCoroutineScope {
        return AppCoroutineScope(
            default = CoroutineScope(Job() + dispatchers.computation),
            io = CoroutineScope(Job() + dispatchers.io),
            main = NsQueueCoroutineScope(),
        )
    }
}
