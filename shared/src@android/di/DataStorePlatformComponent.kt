package di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import base.model.AppCoroutineScope
import base.utils.ApplicationReference
import me.tatarka.inject.annotations.Provides
import software.amazon.lastmile.kotlin.inject.anvil.AppScope
import software.amazon.lastmile.kotlin.inject.anvil.ContributesTo
import software.amazon.lastmile.kotlin.inject.anvil.SingleIn
import utils.DATA_STORE_FILE_NAME
import utils.createDataStore

@ContributesTo(AppScope::class)
interface DataStorePlatformComponent {
    @Provides
    @SingleIn(AppScope::class)
    fun provideDataStore(
        applicationReference: ApplicationReference,
        scope: AppCoroutineScope
    ): DataStore<Preferences> =
        createDataStore(
            coroutineScope = scope.io,
            produceFile = {
                requireNotNull(applicationReference.get())
                    .filesDir
                    .resolve(DATA_STORE_FILE_NAME)
                    .absolutePath
            }
        )
}
