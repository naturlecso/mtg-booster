package network.di

import io.ktor.client.engine.HttpClientEngine

expect object PlatformHttpClientEngineFactory {
    fun create(): HttpClientEngine
}