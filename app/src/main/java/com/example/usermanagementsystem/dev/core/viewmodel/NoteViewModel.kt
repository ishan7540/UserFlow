package com.example.usermanagementsystem.dev.core.viewmodel

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.*
import com.example.usermanagementsystem.dev.core.database.NoteDatabase
import com.example.usermanagementsystem.dev.core.models.ChatMessage
import com.example.usermanagementsystem.dev.core.models.Note
import com.example.usermanagementsystem.dev.core.repository.NoteRepository
import com.google.ai.client.generativeai.Chat
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.content
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NoteViewModel(application: Application) : AndroidViewModel(application) {

    private val sharedPrefs = application.getSharedPreferences("user_session", Context.MODE_PRIVATE)

    private val repository: NoteRepository
    private val _allNotes = MutableLiveData<List<Note>>()
    val allNotes: LiveData<List<Note>> = _allNotes

    private val _chatMessages = MutableLiveData<List<ChatMessage>>(emptyList())
    val chatMessages: LiveData<List<ChatMessage>> = _chatMessages

    private var chat: Chat? = null

    private val generativeModel = GenerativeModel(
        modelName = "gemini-2.5-flash",
        apiKey = "AIzaSyDmuYp5VIEhHYub8NqJjy-WQcM4XJFmBkc"
    )

    init {
        val dao = NoteDatabase.getDatabase(application).noteDao()
        repository = NoteRepository(dao)

        getSessionEmail()?.let { email ->
            loadNotesForEmail(email)
        }

        viewModelScope.launch(Dispatchers.IO) {
            initChat()
        }
    }

    fun saveUserSession(name: String, email: String) {
        sharedPrefs.edit()
            .putString("user_name", name)
            .putString("user_email", email)
            .apply()

        loadNotesForEmail(email)
    }

    fun setSession(email: String, name: String) {
        saveUserSession(name, email)
    }

    fun getSessionEmail(): String? = sharedPrefs.getString("user_email", null)
    fun getSessionName(): String? = sharedPrefs.getString("user_name", null)

    fun loadNotesForEmail(email: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getNotesByEmail(email).collect { notes ->
                _allNotes.postValue(notes)
            }
        }
    }

    fun insert(note: Note) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(note)
        getSessionEmail()?.let { loadNotesForEmail(it) }
    }

    fun delete(note: Note) = viewModelScope.launch(Dispatchers.IO) {
        repository.delete(note)
        getSessionEmail()?.let { loadNotesForEmail(it) }
    }

    fun update(note: Note) = viewModelScope.launch(Dispatchers.IO) {
        repository.update(note)
        getSessionEmail()?.let { loadNotesForEmail(it) }
    }

    private suspend fun initChat() {

            chat = generativeModel.startChat(
                history = listOf(
                    content(role = "user") { text("My name is ${getSessionName() ?: "User"}.") },
                    content(role = "model") { text("Great to meet you. What would you like to know?") }
                )
            )

    }

    fun sendMessage(message: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val updatedList = _chatMessages.value.orEmpty() + ChatMessage(message, 1)
            _chatMessages.postValue(updatedList.sortedBy { it.timestamp })


                val email = getSessionEmail()
                val notesList = email?.let { repository.getNotesByEmailOnce(it) } ?: emptyList()
                val notesContext = if (notesList.isNotEmpty()) {
                    buildString {
                        append("Here are the user's saved notes:\n")
                        notesList.forEach { note ->
                            append("- ${note.noteTitle}: ${note.noteDesc}\n")
                        }
                    }
                } else {
                    " no notes created yet "
                }

                val prompt = "$notesContext\n\nUser's Question: $message"
                val response = chat?.sendMessage(prompt)

                response?.text?.let {
                    val finalList = _chatMessages.value.orEmpty() + ChatMessage(it, 0)
                    _chatMessages.postValue(finalList.sortedBy { it.timestamp })
                }
            }
        }
}
