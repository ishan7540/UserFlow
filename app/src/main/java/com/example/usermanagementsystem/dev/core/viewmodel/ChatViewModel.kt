package com.example.usermanagementsystem.dev.core.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.content
import kotlinx.coroutines.launch

class ChatViewModel(application: Application) : AndroidViewModel(application) {



    private val generativeModel = GenerativeModel(
        modelName = "gemini-2.5-flash",
        apiKey = "API_KEY"
    )

    fun sendInitialMessage() {
        viewModelScope.launch {
            val chat = generativeModel.startChat(
                history = listOf(
                    content(role = "user") { text("My name is Ishan. I am 21 years old") },
                    content(role = "model") {
                        text("Great to meet you. What would you like to know?")
                    }
                )
            )

            val response = chat.sendMessage("in what year was i born?")
            Log.d("ChatViewModel", "Response: ${response.text}")
        }
    }
}
