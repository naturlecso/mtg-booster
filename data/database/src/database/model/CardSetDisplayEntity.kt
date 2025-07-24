package database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = CardSetDisplayEntity.TABLE_NAME)
data class CardSetDisplayEntity(
    @PrimaryKey val code: String,
    val name: String,
    val cardNumber: Int
) {
    companion object {
        const val TABLE_NAME = "card_set_display"
    }
}
