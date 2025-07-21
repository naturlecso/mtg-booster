package data.repository

import domain.CardSet
import domain.SelectedCardSet
import kotlinx.coroutines.flow.Flow

interface CardSetRepository {
    suspend fun fetch()
    suspend fun selectCardSet(code: String)
    suspend fun shuffleCardImageOnSelectedCardSet()
    fun observeCardSets(): Flow<List<CardSet>>
    fun observeSelectedCardSet(): Flow<SelectedCardSet>
}
