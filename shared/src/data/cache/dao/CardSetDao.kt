package data.cache.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import data.cache.model.CardSetEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CardSetDao {
    @Insert
    suspend fun insert(item: CardSetEntity)

    @Query("SELECT * FROM CardSetEntity")
    fun get(): Flow<CardSetEntity>
}
