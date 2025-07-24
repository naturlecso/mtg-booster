package datastore.repository

import kotlinx.coroutines.flow.Flow

interface DataStoreRepository {
    suspend fun saveSelectedCardSet(code: String)
    fun observeSelectedCardSet(): Flow<String>
}
