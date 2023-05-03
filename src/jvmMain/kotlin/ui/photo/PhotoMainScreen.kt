package ui.photo

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.res.loadImageBitmap
import androidx.compose.ui.unit.dp
import java.io.File

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun PhotoMainScreen() {
    var isDragging by remember { mutableStateOf(false) }
    var text by remember { mutableStateOf<String?>(null) }
    var path by remember { mutableStateOf<String?>(null) }

    Column(modifier = Modifier.padding(20.dp)) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    when {
                        isDragging -> Color.Green
                        text != null -> Color.White
                        else -> Color.Red
                    }
                )
                .onExternalDrag(
                    onDragStart = {
                        isDragging = true
                    },
                    onDragExit = {
                        isDragging = false
                    },
                    onDrag = {

                    },
                    onDrop = { state ->
                        val dragData = state.dragData
                        text = dragData.toString()
                        if (dragData is DragData.FilesList) {
                            path = dragData.readFiles()[0]
                        }
                        isDragging = false
                    })
        ) {
            Text(text ?: "Try to drag some files or image here", modifier = Modifier.align(Alignment.Center))
            path?.let {
                val file = File(it.substring(5))
                val imageBitmap: ImageBitmap = remember(file) {
                    loadImageBitmap(file.inputStream())
                }

                Image(
                    painter = BitmapPainter(image = imageBitmap),
                    contentDescription = null
                )
            }
        }
    }
}