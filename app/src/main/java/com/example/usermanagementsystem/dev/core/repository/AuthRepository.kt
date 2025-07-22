package com.example.usermanagementsystem.dev.core.repository

import com.example.usermanagementsystem.dev.core.database.UserDao
import com.example.usermanagementsystem.dev.core.models.User

class AuthRepository(private val userDao: UserDao) {
    suspend fun login(email: String, password: String): String {
        val user = userDao.getUserByEmail(email)
        return when {
            user == null -> "Account does not exist"
            user.password != password -> "Wrong password"
            else -> "success"
        }
    }

    suspend fun register(user: User): Boolean {
            userDao.insert(user)
            return true
    }

    suspend fun getUser(email: String): User? = userDao.getUserByEmail(email)
}
