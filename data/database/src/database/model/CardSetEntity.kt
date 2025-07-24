package database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = CardSetEntity.TABLE_NAME)
data class CardSetEntity(
    @PrimaryKey val code: String,
    val name: String,
    val releasedAt: String, // YYYY-MM-DD
    val iconUri: String,
    val cardCount: Int
) {
    companion object {
        const val TABLE_NAME = "card_set"
    }
}
