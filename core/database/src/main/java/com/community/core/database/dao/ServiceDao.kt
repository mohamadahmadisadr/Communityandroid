package com.community.core.database.dao

import androidx.room.*
import com.community.core.database.entity.CachedServiceEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ServiceDao {
    @Query("SELECT * FROM cached_services ORDER BY title ASC")
    fun getAllServices(): Flow<List<CachedServiceEntity>>
    
    @Query("SELECT * FROM cached_services WHERE id = :id")
    suspend fun getServiceById(id: String): CachedServiceEntity?
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertService(service: CachedServiceEntity)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertServices(services: List<CachedServiceEntity>)
    
    @Delete
    suspend fun deleteService(service: CachedServiceEntity)
    
    @Query("DELETE FROM cached_services")
    suspend fun deleteAllServices()
}
