package store

import database.dao.CardSetDao
import database.model.CardSetEntity
import me.tatarka.inject.annotations.Inject
import network.api.CardSetApi
import network.model.CardSetNetworkModel
import org.mobilenativefoundation.store.store5.Fetcher
import org.mobilenativefoundation.store.store5.SourceOfTruth
import org.mobilenativefoundation.store.store5.Store
import store.extensions.storeBuilder

@Inject
class CardSetStore(
    private val client: CardSetApi,
    private val cardSetDao: CardSetDao
) : Store<Unit, List<CardSetEntity>> by storeBuilder(
    fetcher = Fetcher.of { _ ->
        client.fetchAllCardSets()
            .data
            .filter { setOf("core", "expansion").contains(it.setType) }
            .filter { it.cardCount > 50 } // drop small sets
            .filterNot { it.digital }
            .filterNot { it.releasedAt == null }
    },
    sourceOfTruth = SourceOfTruth.of<Unit, List<CardSetNetworkModel>, List<CardSetEntity>>(
        reader = { cardSetDao.getAll() },
        writer = { _, result ->
            result
                .map { it.asCardSetEntity() }
                .let { cardSetDao.insertAll(it) }
        },
        deleteAll = { cardSetDao.deleteAll() }
    )
).build()

private fun CardSetNetworkModel.asCardSetEntity() = CardSetEntity(
    code = code,
    name = name,
    releasedAt = requireNotNull(releasedAt),
    iconUri = iconSvgUri,
    cardCount = cardCount
)
