package com.example.usermanagementsystem.dev.core.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.usermanagementsystem.dev.core.models.Note
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(note: Note)

    @Update
    suspend fun update(note: Note)

    @Delete
    suspend fun delete(note: Note)

    @Query("SELECT * FROM notes ORDER BY id DESC")
    fun getAllNotes(): LiveData<List<Note>>

    @Query("SELECT * FROM notes")
    suspend fun getAllNotesOnce(): List<Note>

    @Query("SELECT * FROM notes WHERE email = :email")
    fun getNotesByEmail(email: String): Flow<List<Note>>

    @Query("SELECT * FROM notes WHERE email = :email")
    suspend fun getNotesByEmailOnce(email: String): List<Note>


}
