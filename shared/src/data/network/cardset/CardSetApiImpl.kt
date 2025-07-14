package data.network.cardset

import data.network.model.CardSetListNetworkModel
import data.network.model.CardSetNetworkModel
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.request
import io.ktor.http.HttpMethod
import io.ktor.http.path
import me.tatarka.inject.annotations.Inject
import software.amazon.lastmile.kotlin.inject.anvil.AppScope
import software.amazon.lastmile.kotlin.inject.anvil.ContributesBinding
import software.amazon.lastmile.kotlin.inject.anvil.SingleIn

@Inject
@SingleIn(AppScope::class)
@ContributesBinding(AppScope::class)
class CardSetApiImpl(
    private val httpClient: HttpClient
): CardSetApi {
    override suspend fun fetchSingleCardSet(code: String): CardSetNetworkModel {
        return httpClient.request {
            url {
                method = HttpMethod.Get
                path("sets/$code")
            }
        }.body()
    }

    override suspend fun fetchAllCardSets(): CardSetListNetworkModel {
        return httpClient.request {
            url {
                method = HttpMethod.Get
                path("sets")
            }
        }.body()
    }
}
