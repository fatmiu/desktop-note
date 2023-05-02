package repository

import commiumiusqldelighthockeydata.Note

interface NoteRepository {
    suspend fun insert(note: Note)
    suspend fun selectByDate(date: String): Note?
}