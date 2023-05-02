package repository

import commiumiusqldelighthockeydata.Note
import commiumiusqldelighthockeydata.NoteQueries

class DefaultNoteRepository(private val database: NoteQueries) : NoteRepository {

    override suspend fun insert(note: Note) {
        database.insert(note)
    }

    override suspend fun selectByDate(date: String): Note? {
        return database.selectByDate(date).executeAsOneOrNull()
    }
}