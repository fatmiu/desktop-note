package ui.note

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun NoteScreen(
    state: NoteState,
    onEvent: (NoteEvent) -> Unit,
) {
    println(state.note)
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = state.note?.date ?: "date"
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            modifier = Modifier.weight(1f),
            text = state.note?.note ?: "note"
        )
        Spacer(modifier = Modifier.height(16.dp))
        FloatingActionButton(
            modifier = Modifier.align(alignment = Alignment.End),
            shape = RoundedCornerShape(16.dp),
            onClick = {
                onEvent(NoteEvent.onEdit)
            },
        ) {
            Icon(Icons.Filled.Edit, "edit")
        }
    }
}