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
    val notes: List<Note>? = null,
    val selectedDate: String? = null,
    val monthOffset: Int = 0
)

sealed class NoteEvent {
    object OnEdit : NoteEvent()
    object OnDiscard : NoteEvent()
    object OnNextMonth : NoteEvent()
    object OnPreviousMonth : NoteEvent()
    data class OnDateSelect(val date: String) : NoteEvent()
    data class OnSave(val note: Note) : NoteEvent()
}

class NoteViewModel(private val repository: NoteRepository) {

    var state by mutableStateOf(NoteState())
        private set

    init {
        selectByDate(state.selectedDate ?: LocalDateTime.now().toLocalDate().toString())
        selectAll()
    }

    fun selectAll() {
        CoroutineScope(Dispatchers.IO).launch {
            repository.selectAll().collect {
                state = state.copy(notes = it)
            }
        }
    }

    fun selectByDate(date: String) {
        CoroutineScope(Dispatchers.IO).launch {
            // TODO: check db connection
            try {
                state = state.copy(note = repository.selectByDate(date), selectedDate = date)
            } catch (e: Exception) {
                delay(1000)
                selectByDate(date)
            }
        }
    }

    fun insert(note: Note) {
        CoroutineScope(Dispatchers.IO).launch {
            repository.insert(note) {
                selectAll()
            }
        }
    }

    fun setSelectedDate(date: String) {
        selectByDate(date)
        state = state.copy(selectedDate = date)
    }

    fun nextMonth() {
        val offset = state.monthOffset + 1
        state = state.copy(monthOffset = offset)
    }

    fun previousMonth() {
        val offset = state.monthOffset - 1
        state = state.copy(monthOffset = offset)
    }
}