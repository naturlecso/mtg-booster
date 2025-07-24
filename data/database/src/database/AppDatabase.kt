package database

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import database.dao.CardSetDao
import database.dao.CardSetDisplayDao
import database.model.CardSetDisplayEntity
import database.model.CardSetEntity

@Database(
    version = 1,
    entities = [
        CardSetEntity::class,
        CardSetDisplayEntity::class
    ]
)
@ConstructedBy(AppDatabaseConstructor::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getCardSetDao(): CardSetDao
    abstract fun getCardSetDisplayDao(): CardSetDisplayDao
}

expect object AppDatabaseConstructor : RoomDatabaseConstructor<AppDatabase> {
    override fun initialize(): AppDatabase
}
