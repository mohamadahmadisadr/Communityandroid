package com.community.core.database.dao

import androidx.room.*
import com.community.core.database.entity.CachedUserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Query("SELECT * FROM cached_users WHERE id = :id")
    suspend fun getUserById(id: String): CachedUserEntity?
    
    @Query("SELECT * FROM cached_users WHERE email = :email")
    suspend fun getUserByEmail(email: String): CachedUserEntity?
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: CachedUserEntity)
    
    @Update
    suspend fun updateUser(user: CachedUserEntity)
    
    @Delete
    suspend fun deleteUser(user: CachedUserEntity)
    
    @Query("DELETE FROM cached_users")
    suspend fun deleteAllUsers()
}
