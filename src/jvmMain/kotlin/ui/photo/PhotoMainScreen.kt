package ui.photo

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import di.AppModule.photoViewModel

@Composable
fun PhotoMainScreen() {
    val viewModel = photoViewModel

    LaunchedEffect(true) {
        viewModel.getPhotoList()
    }

    PhotoListScreen(
        viewModel.state,
        onEvent = { event -> viewModel.onEvent(event) }
    )
}