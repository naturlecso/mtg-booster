package navigation

import kotlinx.serialization.Serializable

@Serializable
sealed interface RootDestinationConfig {
    @Serializable
    data object Home : RootDestinationConfig
}
