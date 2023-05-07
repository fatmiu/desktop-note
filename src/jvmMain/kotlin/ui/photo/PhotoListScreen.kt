package ui.photo

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.onClick
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import io.kamel.image.KamelImage
import io.kamel.image.lazyPainterResource
import util.GoogleDriveUtil.parseContentLink

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PhotoListScreen(
    state: PhotoState,
    onEvent: (PhotoEvent) -> Unit,
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(5),
        modifier = Modifier.fillMaxSize()
    ) {
        state.photos?.let { photoList ->
            items(photoList) { file ->
                KamelImage(
                    modifier = Modifier
                        .aspectRatio(1f)
                        .padding(1.dp)
                        .onClick {
                            onEvent(PhotoEvent.OnPhotoClick(file))
                        },
                    resource = lazyPainterResource(data = file.thumbnailLink),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                )
            }
        }
    }

    if (state.selectedPhoto != null) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.6f))
        ) {
            IconButton(
                modifier = Modifier.align(Alignment.TopStart),
                onClick = {
                    onEvent(PhotoEvent.OnClose)
                },
            ) {
                Icon(
                    Icons.Default.Close,
                    contentDescription = "close",
                    tint = Color.White
                )
            }
            KamelImage(
                modifier = Modifier.align(Alignment.Center),
                resource = lazyPainterResource(data = parseContentLink(state.selectedPhoto.webContentLink)),
                contentDescription = null,
                contentScale = ContentScale.Fit,
                onLoading = {
                    onEvent(PhotoEvent.OnLoading(it))
                },
            )
            state.loadingProgress?.let {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center),
                    color = Color.White
                )
            }
        }
    }
}