package com.community.core.database.dao

import androidx.room.*
import com.community.core.database.entity.UserFavoriteEntity
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object for user favorites operations
 */
@Dao
interface FavoriteDao {
    
    @Query("SELECT * FROM user_favorites WHERE userId = :userId ORDER BY addedAt DESC")
    fun getUserFavorites(userId: String): Flow<List<UserFavoriteEntity>>
    
    @Query("SELECT * FROM user_favorites WHERE userId = :userId AND itemType = :itemType ORDER BY addedAt DESC")
    fun getUserFavoritesByType(userId: String, itemType: String): Flow<List<UserFavoriteEntity>>
    
    @Query("SELECT * FROM user_favorites WHERE userId = :userId AND itemId = :itemId AND itemType = :itemType")
    suspend fun getFavorite(userId: String, itemId: String, itemType: String): UserFavoriteEntity?
    
    @Query("SELECT EXISTS(SELECT 1 FROM user_favorites WHERE userId = :userId AND itemId = :itemId AND itemType = :itemType)")
    suspend fun isFavorite(userId: String, itemId: String, itemType: String): Boolean
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(favorite: UserFavoriteEntity)
    
    @Delete
    suspend fun deleteFavorite(favorite: UserFavoriteEntity)
    
    @Query("DELETE FROM user_favorites WHERE userId = :userId AND itemId = :itemId AND itemType = :itemType")
    suspend fun deleteFavoriteByIds(userId: String, itemId: String, itemType: String)
    
    @Query("DELETE FROM user_favorites WHERE userId = :userId")
    suspend fun deleteAllUserFavorites(userId: String)
    
    @Query("SELECT COUNT(*) FROM user_favorites WHERE userId = :userId")
    suspend fun getFavoriteCount(userId: String): Int
}
