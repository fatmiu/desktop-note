package ui.note

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import commiumiusqldelighthockeydata.Note
import util.parseDate
import java.time.LocalDateTime

@Composable
fun NoteScreen(
    state: NoteState,
    onEvent: (NoteEvent) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        DateSelector(state.note, state.selectedDate) {
            onEvent(NoteEvent.OnDateSelect(it))
        }
        Spacer(modifier = Modifier.height(16.dp))
        if (state.note == null) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "note not found",
                    fontSize = 16.sp,
                )
            }
        } else {
            Text(
                modifier = Modifier.weight(1f),
                text = state.note.note
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
        Row(
            modifier = Modifier.align(alignment = Alignment.End),
            verticalAlignment = Alignment.CenterVertically,
        ) {

            Spacer(modifier = Modifier.width(16.dp))
            FloatingActionButton(
                shape = RoundedCornerShape(16.dp),
                onClick = {
                    onEvent(NoteEvent.OnEdit)
                },
            ) {
                Icon(Icons.Filled.Edit, "edit")
            }
        }
    }
}

@Composable
fun DateSelector(note: Note?, selectedDate: String?, onSelect: (date: String) -> Unit) {
    val dateSplit = when {
        note != null -> {
            note.date.split("-")
        }

        selectedDate != null -> {
            selectedDate.split("-")
        }

        else -> {
            LocalDateTime.now().toLocalDate().toString().split("-")
        }
    }

    var yearText by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue(dateSplit[0]))
    }
    var monthText by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue(dateSplit[1]))
    }
    var dayText by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue(dateSplit[2]))
    }

    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedTextField(
            modifier = Modifier.width(120.dp),
            value = yearText,
            onValueChange = { yearText = it },
            label = { Text("Year") }
        )
        Spacer(modifier = Modifier.width(16.dp))
        OutlinedTextField(
            modifier = Modifier.width(120.dp),
            value = monthText,
            onValueChange = { monthText = it },
            label = { Text("Month") }
        )
        Spacer(modifier = Modifier.width(16.dp))
        OutlinedTextField(
            modifier = Modifier.width(120.dp),
            value = dayText,
            onValueChange = { dayText = it },
            label = { Text("Day") }
        )
        Spacer(modifier = Modifier.width(16.dp))
        IconButton(
            onClick = {
                onSelect(parseDate(yearText.text, monthText.text, dayText.text))
            }
        ) {
            Icon(Icons.Filled.Search, "search")
        }
    }
}