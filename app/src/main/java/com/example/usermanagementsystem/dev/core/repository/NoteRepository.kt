package com.example.usermanagementsystem.dev.core.repository

import androidx.lifecycle.LiveData
import com.example.mynotes.database.NoteDao
import com.example.usermanagementsystem.dev.core.models.Note

class NoteRepository(private val noteDao: NoteDao) {
    val allNotes: LiveData<List<Note>> = noteDao.onGetAllNotes()

    suspend fun insert(note: Note) {
        noteDao.onInsert(note)
    }

    suspend fun update(note: Note) {
        noteDao.onUpdate(note)
    }

    suspend fun delete(note: Note) {
        noteDao.onDelete(note)
    }
} 