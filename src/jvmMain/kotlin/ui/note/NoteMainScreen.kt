package ui.note

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
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

@Composable
fun NoteNavigationHost(
    navController: NavController
) {
    val viewModel = noteViewModel

    NavigationHost(navController) {
        composable(Routes.NOTE) {
            NoteScreen(
                state = viewModel.state,
                onEvent = { event ->
                    when (event) {
                        NoteEvent.OnEdit -> navController.navigate(Routes.NOTE_EDIT)
                        is NoteEvent.OnDateSelect -> {
                            viewModel.refresh(event.date)
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

                        is NoteEvent.OnSave -> {
                            viewModel.insert(event.note)
                            navController.navigateBack()
                        }

                        else -> Unit
                    }
                }
            )
        }
    }.build()
}