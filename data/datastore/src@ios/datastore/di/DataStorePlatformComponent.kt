package datastore.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import base.model.AppCoroutineScope
import datastore.utils.DATA_STORE_FILE_NAME
import datastore.utils.createDataStore
import kotlinx.cinterop.ExperimentalForeignApi
import me.tatarka.inject.annotations.Provides
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSURL
import platform.Foundation.NSUserDomainMask
import software.amazon.lastmile.kotlin.inject.anvil.AppScope
import software.amazon.lastmile.kotlin.inject.anvil.ContributesTo
import software.amazon.lastmile.kotlin.inject.anvil.SingleIn

@ContributesTo(AppScope::class)
interface DataStorePlatformComponent {

    @OptIn(ExperimentalForeignApi::class)
    @Provides
    @SingleIn(AppScope::class)
    fun provideDataStore(
        scope: AppCoroutineScope
    ): DataStore<Preferences> =
        createDataStore(
            coroutineScope = scope.io,
            produceFile = {
                val documentDirectory: NSURL? = NSFileManager.Companion.defaultManager.URLForDirectory(
                    directory = NSDocumentDirectory,
                    inDomain = NSUserDomainMask,
                    appropriateForURL = null,
                    create = false,
                    error = null,
                )
                requireNotNull(documentDirectory).path + "/$DATA_STORE_FILE_NAME"
            }
        )
}
