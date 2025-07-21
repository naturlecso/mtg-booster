package di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.Job
import me.tatarka.inject.annotations.Provides
import software.amazon.lastmile.kotlin.inject.anvil.AppScope
import software.amazon.lastmile.kotlin.inject.anvil.ContributesTo
import software.amazon.lastmile.kotlin.inject.anvil.SingleIn
import utils.ApplicationReference
import utils.DATA_STORE_FILE_NAME
import utils.createDataStore

@ContributesTo(AppScope::class)
interface DataStorePlatformComponent {
    @Provides
    @SingleIn(AppScope::class)
    fun provideDataStore(
        applicationReference: ApplicationReference
    ): DataStore<Preferences> =
        createDataStore(
            coroutineScope = CoroutineScope(Job() + Dispatchers.IO), // TODO own AppCoroutineScope
            produceFile = {
                requireNotNull(applicationReference.get())
                    .filesDir
                    .resolve(DATA_STORE_FILE_NAME)
                    .absolutePath
            }
        )
}
