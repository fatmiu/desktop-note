package di

import DriverFactory
import com.miumiu.Database
import com.miumiu.sqldelight.NoteQueries
import io.ktor.client.*
import io.ktor.client.engine.jetty.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import repository.DefaultGoogleDriveRepository
import repository.DefaultNoteRepository
import ui.note.NoteViewModel
import ui.photo.PhotoViewModel

object AppModule {
    private val database by lazy { Database(DriverFactory().createDriver()) }
    private val noteQueries: NoteQueries by lazy { database.noteQueries }

    private val noteRepository by lazy { DefaultNoteRepository(noteQueries) }
    val noteViewModel by lazy { NoteViewModel(noteRepository) }

    private val client by lazy {
        HttpClient(Jetty) {
            install(ContentNegotiation) {
                json(Json {
                    prettyPrint = true
                    isLenient = true
                })
            }
        }
    }
    private val googleDriveRepository by lazy { DefaultGoogleDriveRepository(client) }
    val photoViewModel by lazy { PhotoViewModel(googleDriveRepository) }
}