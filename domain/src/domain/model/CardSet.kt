package domain.model

data class CardSet(
    val code: String,
    val name: String,
    val releasedAt: String, // YYYY-MM-DD
    val iconUri: String
)
