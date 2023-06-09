package repository

import com.miumiu.sqldelight.Note
import kotlinx.coroutines.flow.Flow

interface NoteRepository {
    suspend fun insert(note: Note, onSuccess: () -> Unit)
    suspend fun selectAll(): Flow<List<Note>>
    suspend fun selectByDate(date: String): Note?
}