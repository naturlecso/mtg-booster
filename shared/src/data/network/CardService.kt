package data.network

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import me.tatarka.inject.annotations.Inject

@Inject
class CardService(
    private val client: HttpClient
) {
    suspend fun hello() = client.get("https://example.com/hello")
}
