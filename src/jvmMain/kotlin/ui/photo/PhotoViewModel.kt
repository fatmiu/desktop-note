package ui.photo

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import repository.GoogleDriveRepository
import java.io.File

class PhotoViewModel(private val repository: GoogleDriveRepository) {

    fun greeting() {
        CoroutineScope(Dispatchers.IO).launch {
            println(repository.greeting())
        }
    }

    fun upload(file: File){
        CoroutineScope(Dispatchers.IO).launch {
            println(repository.upload(file))
        }
    }

    fun getPhotoList() {
        CoroutineScope(Dispatchers.IO).launch {
            repository.getPhotoList()
        }
    }
}