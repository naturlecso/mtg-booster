package data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import me.tatarka.inject.annotations.Inject
import software.amazon.lastmile.kotlin.inject.anvil.AppScope
import software.amazon.lastmile.kotlin.inject.anvil.ContributesBinding
import software.amazon.lastmile.kotlin.inject.anvil.SingleIn

@Inject
@SingleIn(AppScope::class)
@ContributesBinding(AppScope::class)
class DataStoreRepositoryImpl(
    private val dataStore: DataStore<Preferences>
): DataStoreRepository {
    override suspend fun saveSelectedCardSet(code: String) {
        dataStore.edit { preferences -> preferences[KEY_SELECTED_CARD_SET] = code }
    }

    override fun observeSelectedCardSet(): Flow<String> = dataStore
        .data.map { preferences -> preferences[KEY_SELECTED_CARD_SET] ?: DEFAULT_SELECTED_CARD_SET }

    companion object {
        val KEY_SELECTED_CARD_SET = stringPreferencesKey("selected_card_set")
        const val DEFAULT_SELECTED_CARD_SET = "fin"
    }
}
