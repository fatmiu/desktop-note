package ui.note

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import commiumiusqldelighthockeydata.Note
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import repository.NoteRepository

data class NoteState(
    val note: Note? = null
)

sealed class NoteEvent {
    object OnEdit : NoteEvent()
    data class OnDateSelect(val date: String): NoteEvent()
}

class NoteViewModel(private val repository: NoteRepository) {

    var state by mutableStateOf(NoteState())
        private set

    fun onEvent(event: NoteEvent) {
        when (event) {
            is NoteEvent.OnDateSelect -> {
                println(event.date)
                refresh(date = event.date)
            }
            else -> Unit
        }
    }

    fun refresh(date: String) {
        CoroutineScope(Dispatchers.IO).launch {
            delay(1000)
            state = state.copy(note = repository.selectByDate(date))
        }
    }
}