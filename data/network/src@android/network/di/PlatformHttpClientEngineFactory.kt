package network.di

import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.okhttp.OkHttp

actual object PlatformHttpClientEngineFactory {
    actual fun create(): HttpClientEngine = OkHttp.create()
}