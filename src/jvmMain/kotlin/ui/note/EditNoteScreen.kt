package ui.note

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp


@Composable
fun NoteEditScreen(
    onBack: () -> Unit
) {
    Column(
        Modifier.fillMaxSize().padding(16.dp),
    ) {
        var text by rememberSaveable(stateSaver = TextFieldValue.Saver) {
            mutableStateOf(TextFieldValue("example", TextRange(0, 7)))
        }
        OutlinedTextField(
            modifier = Modifier.fillMaxSize().weight(1f),
            value = text,
            onValueChange = { text = it },
            label = { Text("Label") }
        )
        Button(
            modifier = Modifier.align(alignment = Alignment.End),
            onClick = { onBack() },
        ) {
            Text("go back")
        }
    }
}