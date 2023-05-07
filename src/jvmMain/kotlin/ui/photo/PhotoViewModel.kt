package ui.photo

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.google.api.services.drive.model.File
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import repository.GoogleDriveRepository

data class PhotoState(
    val photos: List<File>? = null,
    val selectedPhoto: File? = null,
    val loadingProgress: Float? = null
)

sealed class PhotoEvent {
    object OnClose : PhotoEvent()
    data class OnPhotoClick(val photo: File) : PhotoEvent()
    data class OnLoading(val progress: Float) : PhotoEvent()
}

class PhotoViewModel(private val repository: GoogleDriveRepository) {

    var state by mutableStateOf(PhotoState())
        private set

    fun onEvent(event: PhotoEvent) {
        when (event) {
            is PhotoEvent.OnPhotoClick -> {
                state = state.copy(selectedPhoto = event.photo)
            }

            PhotoEvent.OnClose -> {
                state = state.copy(selectedPhoto = null)
            }

            is PhotoEvent.OnLoading -> {
                state = state.copy(loadingProgress = if (event.progress == 1.0f) null else event.progress)
            }
        }
    }

    fun greeting() {
        CoroutineScope(Dispatchers.IO).launch {
            println(repository.greeting())
        }
    }

    fun upload(file: java.io.File) {
        CoroutineScope(Dispatchers.IO).launch {
            println(repository.upload(file))
        }
    }

    fun getPhotoList() {
        CoroutineScope(Dispatchers.IO).launch {
            state = state.copy(photos = repository.getPhotoList())
        }
    }
}