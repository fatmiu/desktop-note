package ui.note

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.loadImageBitmap
import io.kamel.image.KamelImage
import io.kamel.image.lazyPainterResource
import ui.photo.PhotoEvent
import util.GoogleDriveUtil
import java.io.File

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun DragAndDropScreen(
    modifier: Modifier = Modifier,
    imageUrl: String? = null,
    onDragImage: ((file: File) -> Unit)? = null,
    onClearImage: (() -> Unit)? = null
) {
    var isDragging by remember { mutableStateOf(false) }
    var text by remember { mutableStateOf<String?>(null) }
    var path by remember { mutableStateOf<String?>(null) }

    Column(modifier) {
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
            imageUrl?.let {
                KamelImage(
                    modifier = Modifier.align(Alignment.Center),
                    resource = lazyPainterResource(data = GoogleDriveUtil.parseContentLink(imageUrl)),
                    contentDescription = null,
                    contentScale = ContentScale.Fit,
                    onLoading = {
                        // TODO: loading 
                    },
                )
            }
            path?.let {
                val file = File(it.substring(5))
                val imageBitmap: ImageBitmap = remember(file) {
                    loadImageBitmap(file.inputStream())
                }

                Image(
                    painter = BitmapPainter(image = imageBitmap),
                    contentDescription = null
                )

                IconButton(
                    onClick = {
                        path = null
                        onClearImage?.invoke()
                    },
                    modifier = Modifier.align(Alignment.TopEnd)
                ) {
                    Icon(Icons.Default.Close, contentDescription = "close")
                }

                onDragImage?.invoke(file)
            }
        }
    }
}