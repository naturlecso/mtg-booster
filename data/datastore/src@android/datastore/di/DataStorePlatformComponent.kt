package datastore.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import base.model.AppCoroutineScope
import base.utils.ApplicationReference
import datastore.utils.DATA_STORE_FILE_NAME
import datastore.utils.createDataStore
import me.tatarka.inject.annotations.Provides
import software.amazon.lastmile.kotlin.inject.anvil.AppScope
import software.amazon.lastmile.kotlin.inject.anvil.ContributesTo
import software.amazon.lastmile.kotlin.inject.anvil.SingleIn

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
