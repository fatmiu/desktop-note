package repository

import commiumiusqldelighthockeydata.Note
import commiumiusqldelighthockeydata.NoteQueries

class DefaultNoteRepository(private val database: NoteQueries): NoteRepository {

    override fun insert(note: Note) {
        database.insert(note)
    }

    override fun selectByDate(date: String): Note {
        return database.selectByDate(date).executeAsOne()
    }
}