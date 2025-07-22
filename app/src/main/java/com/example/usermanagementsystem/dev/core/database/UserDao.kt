package com.example.usermanagementsystem.dev.core.database

import androidx.room.*
import com.example.usermanagementsystem.dev.core.models.User

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user: User)

    @Query("SELECT * FROM users WHERE email = :email")
    suspend fun getUserByEmail(email: String): User?
}
