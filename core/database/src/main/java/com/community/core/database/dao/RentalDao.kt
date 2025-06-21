package com.community.core.database.dao

import androidx.room.*
import com.community.core.database.entity.CachedRentalEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RentalDao {
    @Query("SELECT * FROM cached_rentals ORDER BY title ASC")
    fun getAllRentals(): Flow<List<CachedRentalEntity>>
    
    @Query("SELECT * FROM cached_rentals WHERE id = :id")
    suspend fun getRentalById(id: String): CachedRentalEntity?
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRental(rental: CachedRentalEntity)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRentals(rentals: List<CachedRentalEntity>)
    
    @Delete
    suspend fun deleteRental(rental: CachedRentalEntity)
    
    @Query("DELETE FROM cached_rentals")
    suspend fun deleteAllRentals()
}
