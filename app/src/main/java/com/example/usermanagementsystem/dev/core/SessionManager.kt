package com.example.usermanagementsystem.dev.core.session

import android.content.Context

class SessionManager(context: Context) {
    private val prefs = context.getSharedPreferences("user_session", Context.MODE_PRIVATE)

    fun saveUser(email: String, name: String) {
        prefs.edit().apply {
            putString("email", email)
            putString("name", name)
            apply()
        }
    }

    fun getEmail(): String? = prefs.getString("email", null)
    fun getName(): String? = prefs.getString("name", null)

    fun clear() {
        prefs.edit().clear().apply()
    }
}
