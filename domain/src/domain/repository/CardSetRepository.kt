package domain.repository

import domain.model.CardSet
import domain.model.SelectedCardSet
import kotlinx.coroutines.flow.Flow

interface CardSetRepository {
    suspend fun fetch()
    suspend fun selectCardSet(code: String)
    suspend fun shuffleCardImageOnSelectedCardSet()
    fun observeCardSets(): Flow<List<CardSet>>
    fun observeSelectedCardSet(): Flow<SelectedCardSet>
}
