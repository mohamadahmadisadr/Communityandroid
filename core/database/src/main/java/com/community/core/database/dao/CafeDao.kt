package com.community.core.database.dao

import androidx.room.*
import com.community.core.database.entity.CachedCafeEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CafeDao {
    @Query("SELECT * FROM cached_cafes ORDER BY name ASC")
    fun getAllCafes(): Flow<List<CachedCafeEntity>>
    
    @Query("SELECT * FROM cached_cafes WHERE id = :id")
    suspend fun getCafeById(id: String): CachedCafeEntity?
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCafe(cafe: CachedCafeEntity)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCafes(cafes: List<CachedCafeEntity>)
    
    @Delete
    suspend fun deleteCafe(cafe: CachedCafeEntity)
    
    @Query("DELETE FROM cached_cafes")
    suspend fun deleteAllCafes()
}
