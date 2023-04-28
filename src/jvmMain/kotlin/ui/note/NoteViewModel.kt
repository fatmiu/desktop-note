package ui.note

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import commiumiusqldelighthockeydata.Note
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import repository.NoteRepository

data class NoteState(
    val note: Note? = null
)

sealed class NoteEvent {
    object onEdit : NoteEvent()
}

class NoteViewModel(private val repository: NoteRepository) {

    var state by mutableStateOf(NoteState())
        private set

    fun onEvent(event: NoteEvent) {
        when (event) {
            NoteEvent.onEdit -> {
                refresh("2023-04-29")
            }
        }
    }

    fun refresh(date: String) {
        CoroutineScope(Dispatchers.IO).launch {
            delay(1000)
            state = state.copy(note = repository.selectByDate(date))
        }
    }
}