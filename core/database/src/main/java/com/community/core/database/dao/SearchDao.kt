package com.community.core.database.dao

import androidx.room.*
import com.community.core.database.entity.SearchHistoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SearchDao {
    @Query("SELECT * FROM search_history ORDER BY searchedAt DESC LIMIT 20")
    fun getRecentSearches(): Flow<List<SearchHistoryEntity>>
    
    @Query("SELECT DISTINCT query FROM search_history WHERE query LIKE '%' || :query || '%' ORDER BY searchedAt DESC LIMIT 10")
    suspend fun getSearchSuggestions(query: String): List<String>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSearch(search: SearchHistoryEntity)
    
    @Query("DELETE FROM search_history WHERE id = :id")
    suspend fun deleteSearch(id: String)
    
    @Query("DELETE FROM search_history")
    suspend fun deleteAllSearches()
}
