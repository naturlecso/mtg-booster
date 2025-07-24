package network.di

import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.darwin.Darwin

actual object PlatformHttpClientEngineFactory {
    actual fun create(): HttpClientEngine = Darwin.create()
}