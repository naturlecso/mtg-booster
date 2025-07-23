package base.model

import kotlinx.serialization.Serializable

@Serializable
data class Configs(
    val isDebug: Boolean,
)
