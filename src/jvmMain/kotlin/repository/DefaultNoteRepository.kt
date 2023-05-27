package repository

import com.miumiu.sqldelight.Note
import com.miumiu.sqldelight.NoteQueries
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class DefaultNoteRepository(private val database: NoteQueries) : NoteRepository {

    override suspend fun insert(note: Note, onSuccess: () -> Unit) {
        database.transaction {
            afterCommit {
                onSuccess()
            }
            database.insert(note)
        }
    }

    override suspend fun selectAll(): Flow<List<Note>> {
        return flow {
            try {
                emit(database.selectAll().executeAsList())
            } catch (e: Exception) {
                delay(1000)
                selectAll()
            }

        }
    }

    override suspend fun selectByDate(date: String): Note? {
        return database.selectByDate(date).executeAsOneOrNull()
    }
}