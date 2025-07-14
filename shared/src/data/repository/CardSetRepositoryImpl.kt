package data.repository

import data.cache.model.CardSetEntity
import data.store.CardSetStore
import domain.CardSet
import domain.SelectedCardSet
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import me.tatarka.inject.annotations.Inject
import org.mobilenativefoundation.store.store5.StoreReadRequest
import software.amazon.lastmile.kotlin.inject.anvil.AppScope
import software.amazon.lastmile.kotlin.inject.anvil.ContributesBinding
import software.amazon.lastmile.kotlin.inject.anvil.SingleIn
import kotlin.String

@Inject
@SingleIn(AppScope::class)
@ContributesBinding(AppScope::class)
class CardSetRepositoryImpl(
    private val cardSetStore: CardSetStore
) : CardSetRepository {
    override suspend fun selectCardSet(code: String) {
        TODO("Not yet implemented")
    }

    override suspend fun shuffleDisplayCardOnSelectedCardSet() {
        TODO("Not yet implemented")
    }

    override fun observeCardSets(): Flow<List<CardSet>> {
        return cardSetStore.stream(StoreReadRequest.cached(key = Unit, refresh = true))
            .map { sets -> sets.dataOrNull()
                ?.map { it.asCardSet() }
                ?: emptyList()
            }
    }

    override fun observeSelectedCardSet(): Flow<SelectedCardSet> {
        TODO("Not yet implemented")
    }

}

private fun CardSetEntity.asCardSet() = CardSet(
    code = code,
    name = name,
    releasedAt = releasedAt,
    iconUri = iconUri
)
