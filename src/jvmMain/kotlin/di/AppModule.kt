package di

import DriverFactory
import com.miumiu.Database
import commiumiusqldelighthockeydata.NoteQueries
import repository.DefaultNoteRepository
import ui.note.NoteViewModel

object AppModule {
    private val database = Database(DriverFactory().createDriver())
    private val noteQueries: NoteQueries = database.noteQueries

    private val noteRepository = DefaultNoteRepository(noteQueries)
    val noteViewModel = NoteViewModel(noteRepository)
}