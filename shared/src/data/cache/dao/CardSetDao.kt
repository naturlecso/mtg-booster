package data.cache.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import data.cache.model.CardSetEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CardSetDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(items: List<CardSetEntity>)

    @Query("SELECT * FROM ${CardSetEntity.TABLE_NAME} WHERE code=:code")
    fun get(code: String): Flow<CardSetEntity>

    @Query("SELECT * FROM ${CardSetEntity.TABLE_NAME}")
    fun getAll(): Flow<List<CardSetEntity>>

    @Query("DELETE FROM ${CardSetEntity.TABLE_NAME}")
    suspend fun deleteAll()
}
