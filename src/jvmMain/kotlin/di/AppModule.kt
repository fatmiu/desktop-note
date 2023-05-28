package di

import DriverFactory
import app.cash.sqldelight.ColumnAdapter
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.json.gson.GsonFactory
import com.google.api.services.drive.Drive
import com.miumiu.Database
import com.miumiu.sqldelight.Note
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
import util.GoogleDriveUtil

object AppModule {
    private val database by lazy { Database(DriverFactory().createDriver()) }
    private val noteQueries: NoteQueries by lazy { database.noteQueries }

    private val googleDriveRepository by lazy { DefaultGoogleDriveRepository(client) }
    private val noteRepository by lazy { DefaultNoteRepository(noteQueries) }

    val noteViewModel by lazy { NoteViewModel(noteRepository, googleDriveRepository) }
    val photoViewModel by lazy { PhotoViewModel(googleDriveRepository) }

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

    val service: Drive by lazy {
        Drive.Builder(
            NetHttpTransport(),
            GsonFactory.getDefaultInstance(),
            GoogleDriveUtil.getRequestInitializer()
        ).setApplicationName("note").build()
    }
}