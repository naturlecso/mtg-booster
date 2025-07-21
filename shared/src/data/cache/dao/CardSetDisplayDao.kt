package data.cache.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import data.cache.model.CardSetDisplayEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CardSetDisplayDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: CardSetDisplayEntity)

    @Query("SELECT * FROM ${CardSetDisplayEntity.TABLE_NAME} WHERE code=:code")
    fun get(code: String): Flow<CardSetDisplayEntity?>

    @Update
    suspend fun update(item: CardSetDisplayEntity)
}
