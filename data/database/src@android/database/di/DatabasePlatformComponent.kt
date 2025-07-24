package database.di

import android.app.Application
import androidx.room.Room
import androidx.room.RoomDatabase
import base.utils.ApplicationReference
import database.AppDatabase
import me.tatarka.inject.annotations.Provides
import software.amazon.lastmile.kotlin.inject.anvil.AppScope
import software.amazon.lastmile.kotlin.inject.anvil.ContributesTo
import software.amazon.lastmile.kotlin.inject.anvil.SingleIn

@ContributesTo(AppScope::class)
interface DatabasePlatformComponent {
    @Provides
    @SingleIn(AppScope::class)
    fun provideRoomBuilder(
        applicationReference: ApplicationReference
    ): RoomDatabase.Builder<AppDatabase> = getDatabaseBuilder(
        application = requireNotNull(applicationReference.get())
    )
}

private fun getDatabaseBuilder(
    application: Application
): RoomDatabase.Builder<AppDatabase> {
    val appContext = application.applicationContext
    val dbFile = appContext.getDatabasePath("mtg_booster.db")
    return Room.databaseBuilder<AppDatabase>(
        context = appContext,
        name = dbFile.absolutePath
    )
}
