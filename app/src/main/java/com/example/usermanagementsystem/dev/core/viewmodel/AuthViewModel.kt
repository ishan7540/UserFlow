package com.example.usermanagementsystem.dev.core.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.example.usermanagementsystem.dev.core.database.NoteDatabase
import com.example.usermanagementsystem.dev.core.models.User
import com.example.usermanagementsystem.dev.core.repository.AuthRepository
import com.example.usermanagementsystem.dev.core.session.SessionManager
import kotlinx.coroutines.launch

class AuthViewModel(application: Application) : AndroidViewModel(application) {

    private val userDao = NoteDatabase.getDatabase(application).userDao()
    private val repository = AuthRepository(userDao)
    private val sessionManager = SessionManager(application)

    private val _authStatus = MutableLiveData<String>()
    val authStatus: LiveData<String> = _authStatus

    private val _authUser = MutableLiveData<User?>()
    val authUser: LiveData<User?> = _authUser

    fun login(email: String, password: String) = viewModelScope.launch {
        val result = repository.login(email, password)
        if (result == "success") {
            val user = repository.getUser(email)
            user?.let {
                sessionManager.saveUser(it.email, it.name)
                _authUser.postValue(it)
            }
        }
        _authStatus.postValue(result)
    }

    fun register(name: String, email: String, password: String) = viewModelScope.launch {
        val success = repository.register(User(email, password, name))
        _authStatus.postValue(if (success) "success" else "Account already exists")
    }

    fun logout() {
        sessionManager.clear()
        _authUser.postValue(null)
    }
}
