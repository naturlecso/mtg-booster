package data.cache

import me.tatarka.inject.annotations.Provides
import software.amazon.lastmile.kotlin.inject.anvil.AppScope
import software.amazon.lastmile.kotlin.inject.anvil.ContributesTo

@ContributesTo(AppScope::class)
interface CacheComponent {
    @Provides
    fun provideCardSetDao(
        database: AppDatabase
    ) = database.getCardSetDao()

    @Provides
    fun provideCardSetDisplayDao(
        database: AppDatabase
    ) = database.getCardSetDisplayDao()
}
