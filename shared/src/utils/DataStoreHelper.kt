@file:OptIn(InternalCoroutinesApi::class)

package utils

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.internal.SynchronizedObject
import kotlinx.coroutines.internal.synchronized
import okio.Path.Companion.toPath

private val lock = SynchronizedObject()
private lateinit var dataStore: DataStore<Preferences>

internal fun createDataStore(produceFile: () -> String, coroutineScope: CoroutineScope) =
    synchronized(lock) {
        if (::dataStore.isInitialized) {
            dataStore
        } else {
            PreferenceDataStoreFactory.createWithPath(
                corruptionHandler = null,
                migrations = emptyList(),
                scope = coroutineScope,
                produceFile = { produceFile().toPath() },
            )
                .also { dataStore = it }
        }
    }

const val DATA_STORE_FILE_NAME = "mtgbooster.preferences_pb"
