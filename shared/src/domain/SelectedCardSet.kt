package domain

data class SelectedCardSet(
    val code: String = "krk",
    val name: String = "Khans of Tarkir",
    val cardNumber: Int = 190
) {
    val cardImageUrl by lazy { "https://www.mtgpics.com/pics/art/$code/$cardNumber.jpg" }
}
