package network.di

import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.HttpRequestRetry
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.headers
import io.ktor.http.HttpHeaders
import io.ktor.http.URLProtocol
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import me.tatarka.inject.annotations.Provides
import software.amazon.lastmile.kotlin.inject.anvil.AppScope
import software.amazon.lastmile.kotlin.inject.anvil.ContributesTo
import co.touchlab.kermit.Logger as KermitLogger

const val TIMEOUT_DURATION: Long = 10_000

@ContributesTo(AppScope::class)
interface NetworkComponent {
    @Provides
    fun provideHttpClient(
        engine: HttpClientEngine
    ): HttpClient = HttpClient(engine) {
        install(ContentNegotiation) {
            json(
                Json {
                    isLenient = true
                    ignoreUnknownKeys = true
                }
            )
        }

        install(DefaultRequest) {
            apply {
                url {
                    protocol = URLProtocol.HTTPS
                    host = "api.scryfall.com"

                    headers {
                        append(HttpHeaders.Accept, "application/vnd.api+json")
                        append(HttpHeaders.ContentType, "application/vnd.api+json")
                        append(HttpHeaders.UserAgent, "MTGBooster/1.0")
                    }
                }
            }
        }

        install(HttpTimeout) {
            requestTimeoutMillis = TIMEOUT_DURATION
            connectTimeoutMillis = TIMEOUT_DURATION
            socketTimeoutMillis = TIMEOUT_DURATION
        }

        install(HttpRequestRetry) {
            retryOnServerErrors(maxRetries = 2)
            exponentialDelay()
        }

        install(Logging) {
            level = LogLevel.ALL
            logger = object : Logger { // TODO show logs only in debug builds
                override fun log(message: String) {
                    KermitLogger.withTag("MtgApi").d(message)
                }
            }
        }
    }

    @Provides
    fun provideHttpClientEngine(): HttpClientEngine =
        PlatformHttpClientEngineFactory.create()
}
