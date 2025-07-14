package data.repository

import domain.CardSet
import domain.SelectedCardSet
import kotlinx.coroutines.flow.Flow

interface CardSetRepository {
    suspend fun selectCardSet(code: String)
    suspend fun shuffleDisplayCardOnSelectedCardSet()
    fun observeCardSets(): Flow<List<CardSet>>
    fun observeSelectedCardSet(): Flow<SelectedCardSet>
}
