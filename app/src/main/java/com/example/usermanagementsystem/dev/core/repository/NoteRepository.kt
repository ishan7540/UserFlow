

    package com.example.usermanagementsystem.dev.core.repository

    import androidx.lifecycle.LiveData
    import com.example.usermanagementsystem.dev.core.database.NoteDao
    import com.example.usermanagementsystem.dev.core.models.Note
    import kotlinx.coroutines.flow.Flow

    class NoteRepository(private val noteDao: NoteDao) {

        val allNotes: LiveData<List<Note>> = noteDao.getAllNotes()

        suspend fun insert(note: Note) {
            noteDao.insert(note)
        }

        suspend fun update(note: Note) {
            noteDao.update(note)
        }

        suspend fun delete(note: Note) {
            noteDao.delete(note)
        }

        suspend fun getAllNotesOnce(): List<Note> {
            return noteDao.getAllNotesOnce()
        }

        fun getNotesByEmail(email: String): Flow<List<Note>> {
            return noteDao.getNotesByEmail(email)
        }

        suspend fun getNotesByEmailOnce(email: String): List<Note> {
            return noteDao.getNotesByEmailOnce(email)
        }


    }
