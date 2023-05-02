package repository

import commiumiusqldelighthockeydata.Note

interface NoteRepository {
    fun insert(note: Note)
    fun selectByDate(date: String): Note?
}