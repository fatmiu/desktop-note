package ui.note

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import di.AppModule.noteViewModel
import util.*

@Composable
fun NoteMainScreen() {
    val navController by rememberNavController(Routes.NOTE)

    Box(
        modifier = Modifier.fillMaxSize(),
    ) {
        NoteNavigationHost(navController)
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun NoteNavigationHost(
    navController: NavController
) {
    val viewModel = noteViewModel

    NavigationHost(navController) {
        composable(Routes.NOTE) {
            NoteGridScreen(
                state = viewModel.state,
                onEvent = { event ->
                    when (event) {
                        is NoteEvent.OnDateSelect -> {
                            viewModel.setSelectedDate(event.date)
                            navController.navigate(Routes.NOTE_EDIT)
                        }

                        NoteEvent.OnNextMonth -> {
                            viewModel.nextMonth()
                        }

                        NoteEvent.OnPreviousMonth -> {
                            viewModel.previousMonth()
                        }

                        else -> Unit
                    }
                }
            )
        }
        composable(Routes.NOTE_EDIT) {
            NoteEditScreen(
                state = viewModel.state,
                onEvent = { event ->
                    when (event) {
                        NoteEvent.OnDiscard -> {
                            navController.navigateBack()
                        }

                        NoteEvent.OnClearImage -> {
                            viewModel.clearTempImage()
                        }

                        is NoteEvent.OnSave -> {
                            viewModel.save(event.note)
                            navController.navigateBack()
                        }

                        is NoteEvent.OnDragImage -> {
                            viewModel.setTempImage(image = event.file)
                        }

                        else -> Unit
                    }
                }
            )
        }
    }.build()
}