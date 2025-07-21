package com.example.mynotes.database


import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.usermanagementsystem.dev.core.models.Note


@Dao
interface NoteDao {
    @Insert (onConflict = OnConflictStrategy.REPLACE)
    suspend fun onInsert(note: Note)
    @Delete
    suspend fun onDelete(note:Note)
    @Update
    suspend fun onUpdate(note: Note)
    @Query ("SELECT * FROM notes ORDER BY id DESC")
    fun onGetAllNotes(): LiveData<List<Note>>
    @Query("SELECT * FROM notes WHERE noteTitle LIKE:query OR noteDesc LIKE:query")
    fun onSearchNote(query:String?) : LiveData<List<Note>>

}