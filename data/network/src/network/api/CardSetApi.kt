package network.api

import network.model.CardSetListNetworkModel
import network.model.CardSetNetworkModel

interface CardSetApi {
    suspend fun fetchSingleCardSet(code: String): CardSetNetworkModel
    suspend fun fetchAllCardSets(): CardSetListNetworkModel
}
