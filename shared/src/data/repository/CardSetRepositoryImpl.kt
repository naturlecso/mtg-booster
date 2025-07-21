package data.repository

import data.cache.dao.CardSetDao
import data.cache.dao.CardSetDisplayDao
import data.cache.model.CardSetDisplayEntity
import data.cache.model.CardSetEntity
import data.store.CardSetStore
import domain.CardSet
import domain.SelectedCardSet
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import me.tatarka.inject.annotations.Inject
import org.mobilenativefoundation.store.store5.StoreReadRequest
import org.mobilenativefoundation.store.store5.impl.extensions.fresh
import software.amazon.lastmile.kotlin.inject.anvil.AppScope
import software.amazon.lastmile.kotlin.inject.anvil.ContributesBinding
import software.amazon.lastmile.kotlin.inject.anvil.SingleIn

@Inject
@SingleIn(AppScope::class)
@ContributesBinding(AppScope::class)
class CardSetRepositoryImpl(
    private val cardSetStore: CardSetStore,
    private val cardSetDao: CardSetDao,
    private val cardSetDisplayDao: CardSetDisplayDao,
    private val dataStoreRepository: DataStoreRepository
) : CardSetRepository {
    override suspend fun fetch() {
        cardSetStore
            .fresh(Unit)
            .first()
            .code
            .let { selectCardSet(it) }
    }

    override suspend fun selectCardSet(code: String) {
        val cardSetHasSavedDisplayCard = cardSetDisplayDao
            .get(code)
            .firstOrNull() != null
        if (!cardSetHasSavedDisplayCard) {
            cardSetDao
                .get(code)
                .first()
                .let { cardSetDisplayDao.insert(it.asCardSetDisplay()) }
        }

        dataStoreRepository.saveSelectedCardSet(code)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override suspend fun shuffleCardImageOnSelectedCardSet() {
        dataStoreRepository.observeSelectedCardSet()
            .flatMapLatest { code ->
                combine(
                    cardSetDao.get(code).map { it.cardCount },
                    cardSetDisplayDao.get(code).filterNotNull()
                ) { cardCountInSet, cardSetDisplay ->
                    cardSetDisplay.copy(cardNumber = cardCountInSet.randomUpToMyself()) }
            }
            .first()
            .let { cardSetDisplayDao.update(it) }
    }

    override fun observeCardSets(): Flow<List<CardSet>> = cardSetStore
        .stream(StoreReadRequest.cached(key = Unit, refresh = false))
        .map { sets ->
            sets.dataOrNull()
                ?.map { it.asCardSet() }
                ?: emptyList()
        }

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun observeSelectedCardSet(): Flow<SelectedCardSet> = dataStoreRepository
        .observeSelectedCardSet()
        .flatMapLatest(cardSetDisplayDao::get)
        .filterNotNull()
        .map { it.asCardSetDisplay() }
}

private fun Int.randomUpToMyself() = (1..this).random()

private fun CardSetEntity.asCardSet() = CardSet(
    code = code,
    name = name,
    releasedAt = releasedAt,
    iconUri = iconUri
)

private fun CardSetDisplayEntity.asCardSetDisplay() = SelectedCardSet(
    code = code,
    name = name,
    cardNumber = cardNumber
)

private fun CardSetEntity.asCardSetDisplay() = CardSetDisplayEntity(
    code = code,
    name = name,
    cardNumber = cardCount.randomUpToMyself()
)
