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
import java.time.LocalDateTime

data class NoteState(
    val note: Note? = null,
    val selectedDate: String? = null
)

sealed class NoteEvent {
    object OnEdit : NoteEvent()
    object OnDiscard : NoteEvent()
    data class OnDateSelect(val date: String) : NoteEvent()
    data class OnSave(val note: Note) : NoteEvent()
}

class NoteViewModel(private val repository: NoteRepository) {

    var state by mutableStateOf(NoteState())
        private set

    init {
        refresh(state.selectedDate ?: LocalDateTime.now().toLocalDate().toString())
    }

    fun refresh(date: String) {
        CoroutineScope(Dispatchers.IO).launch {
            // TODO: check db connection
            try {
                state = state.copy(note = repository.selectByDate(date), selectedDate = date)
            } catch (e: Exception) {
                delay(1000)
                refresh(date)
            }
        }
    }

    fun insert(note: Note) {
        CoroutineScope(Dispatchers.IO).launch {
            repository.insert(note)
            // TODO: correct this flow after implement coroutine sql delight ext
            state = state.copy(note = note)
        }
    }
}