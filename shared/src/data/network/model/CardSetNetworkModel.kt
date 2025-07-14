package data.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CardSetListNetworkModel(
    val data: List<CardSetNetworkModel>
)

@Serializable
data class CardSetNetworkModel(
    val code: String,
    val name: String,
    val digital: Boolean,
    @SerialName("released_at") val releasedAt: String?, // YYYY-MM-DD
    @SerialName("set_type") val setType: String,
    @SerialName("card_count") val cardCount: Int,
    @SerialName("icon_svg_uri") val iconSvgUri: String
)
