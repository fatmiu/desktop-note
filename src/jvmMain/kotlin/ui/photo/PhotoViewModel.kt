package ui.photo

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import repository.GoogleDriveRepository

class PhotoViewModel(private val repository: GoogleDriveRepository) {

    fun greeting() {
        CoroutineScope(Dispatchers.IO).launch {
            println(repository.greeting())
        }
    }
}