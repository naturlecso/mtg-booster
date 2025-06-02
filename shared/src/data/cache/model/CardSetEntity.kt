package data.cache.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CardSetEntity(
    @PrimaryKey val code: String,
    val name: String,
    val imageUrl: String,
    val cardCount: Int
)
