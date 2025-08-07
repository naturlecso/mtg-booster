package repository

import database.dao.CardSetDao
import database.dao.CardSetDisplayDao
import database.model.CardSetDisplayEntity
import database.model.CardSetEntity
import datastore.repository.DataStoreRepository
import domain.model.CardSet
import domain.model.SelectedCardSet
import domain.repository.CardSetRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import me.tatarka.inject.annotations.Inject
import org.mobilenativefoundation.store.store5.StoreReadRequest
import org.mobilenativefoundation.store.store5.StoreReadResponse
import software.amazon.lastmile.kotlin.inject.anvil.AppScope
import software.amazon.lastmile.kotlin.inject.anvil.ContributesBinding
import software.amazon.lastmile.kotlin.inject.anvil.SingleIn
import store.CardSetStore

@Inject
@SingleIn(AppScope::class)
@ContributesBinding(AppScope::class)
class CardSetRepositoryImpl(
    private val cardSetStore: CardSetStore,
    private val cardSetDao: CardSetDao,
    private val cardSetDisplayDao: CardSetDisplayDao,
    private val dataStoreRepository: DataStoreRepository
) : CardSetRepository {
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
    override suspend fun shuffleCardImageOnSelectedCardSet() = dataStoreRepository.observeSelectedCardSet()
        .filterNotNull()
        .flatMapLatest { code ->
            combine(
                cardSetDao.get(code)
                    .map { it.cardCount },
                cardSetDisplayDao.get(code)
                    .filterNotNull()
            ) { cardCountInSet, cardSetDisplay ->
                cardSetDisplay.copy(cardNumber = cardCountInSet.randomUpToMyself())
            }
        }
        .first()
        .let { cardSetDisplayDao.update(it) }

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
        .flatMapLatest { selectedCardSetCode ->
            when {
                selectedCardSetCode != null -> cardSetDisplayDao
                    .get(selectedCardSetCode)
                    .filterNotNull() // TODO handle null
                else -> cardSetStore
                    .stream(StoreReadRequest.cached(key = Unit, refresh = true))
                    .filterIsInstance<StoreReadResponse.Data<List<CardSetEntity>>>()
                    .map { it.requireData() }
                    .filter { it.isNotEmpty() }
                    .map { cardSetList -> cardSetList
                        .random()
                        .asCardSetDisplay()
                    }
                    .onEach {
                        cardSetDisplayDao.insert(it)
                        dataStoreRepository.saveSelectedCardSet(it.code)
                    }
            }
        }
        .map { it.asSelectedCardSet() }
}

private fun Int.randomUpToMyself() = (1..this).random()

private fun CardSetEntity.asCardSet() = CardSet(
    code = code,
    name = name,
    releasedAt = releasedAt,
    iconUri = iconUri
)

private fun CardSetDisplayEntity.asSelectedCardSet() = SelectedCardSet(
    code = code,
    name = name,
    cardNumber = cardNumber
)

private fun CardSetEntity.asCardSetDisplay() = CardSetDisplayEntity(
    code = code,
    name = name,
    cardNumber = cardCount.randomUpToMyself()
)
