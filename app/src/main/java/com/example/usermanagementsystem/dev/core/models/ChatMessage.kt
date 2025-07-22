package com.example.usermanagementsystem.dev.core.models

data class ChatMessage(
    val message: String,
    val isSent: Int,
    val timestamp: Long = System.currentTimeMillis()
)
