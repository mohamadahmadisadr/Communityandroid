package com.community.core.database.dao

import androidx.room.*
import com.community.core.database.entity.CachedEventEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface EventDao {
    @Query("SELECT * FROM cached_events ORDER BY title ASC")
    fun getAllEvents(): Flow<List<CachedEventEntity>>
    
    @Query("SELECT * FROM cached_events WHERE id = :id")
    suspend fun getEventById(id: String): CachedEventEntity?
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEvent(event: CachedEventEntity)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEvents(events: List<CachedEventEntity>)
    
    @Delete
    suspend fun deleteEvent(event: CachedEventEntity)
    
    @Query("DELETE FROM cached_events")
    suspend fun deleteAllEvents()
}
