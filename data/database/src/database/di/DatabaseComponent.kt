package database.di

import androidx.room.RoomDatabase
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import database.AppDatabase
import me.tatarka.inject.annotations.Provides
import software.amazon.lastmile.kotlin.inject.anvil.AppScope
import software.amazon.lastmile.kotlin.inject.anvil.ContributesTo

@ContributesTo(AppScope::class)
interface DatabaseComponent {
    @Provides
    fun provideAppDatabase(
        builder: RoomDatabase.Builder<AppDatabase>
    ): AppDatabase = builder
        .fallbackToDestructiveMigrationOnDowngrade(true)
        .setDriver(BundledSQLiteDriver())
        .build()

    @Provides
    fun provideCardSetDao(
        database: AppDatabase
    ) = database.getCardSetDao()

    @Provides
    fun provideCardSetDisplayDao(
        database: AppDatabase
    ) = database.getCardSetDisplayDao()
}
