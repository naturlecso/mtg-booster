package data.network.cardset

import data.network.model.CardSetListNetworkModel
import data.network.model.CardSetNetworkModel

interface CardSetApi {
    suspend fun fetchSingleCardSet(code: String): CardSetNetworkModel
    suspend fun fetchAllCardSets(): CardSetListNetworkModel
}
