package com.community.core.database.dao

import androidx.room.*
import com.community.core.database.entity.CachedJobEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface JobDao {
    @Query("SELECT * FROM cached_jobs ORDER BY title ASC")
    fun getAllJobs(): Flow<List<CachedJobEntity>>
    
    @Query("SELECT * FROM cached_jobs WHERE id = :id")
    suspend fun getJobById(id: String): CachedJobEntity?
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertJob(job: CachedJobEntity)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertJobs(jobs: List<CachedJobEntity>)
    
    @Delete
    suspend fun deleteJob(job: CachedJobEntity)
    
    @Query("DELETE FROM cached_jobs")
    suspend fun deleteAllJobs()
}
