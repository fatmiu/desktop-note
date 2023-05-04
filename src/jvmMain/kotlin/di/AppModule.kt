package di

import DriverFactory
import com.miumiu.Database
import com.miumiu.sqldelight.NoteQueries
import repository.DefaultNoteRepository
import ui.note.NoteViewModel

object AppModule {
    private val database by lazy { Database(DriverFactory().createDriver()) }
    private val noteQueries: NoteQueries by lazy { database.noteQueries }

    private val noteRepository by lazy { DefaultNoteRepository(noteQueries) }
    val noteViewModel by lazy { NoteViewModel(noteRepository) }
}