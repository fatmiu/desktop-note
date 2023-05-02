package ui.note

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import di.AppModule.noteViewModel
import util.*
import java.time.LocalDateTime

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
    viewModel.refresh(LocalDateTime.now().toLocalDate().toString())

    NavigationHost(navController) {
        composable(Routes.NOTE) {
            NoteScreen(
                state = viewModel.state,
                onEvent = { event ->
                    when (event) {
                        NoteEvent.OnEdit -> navController.navigate(Routes.NOTE_EDIT)
                        else -> {
                            viewModel.onEvent(event)
                        }
                    }
                }
            )
        }
        composable(Routes.NOTE_EDIT) {
            NoteEditScreen {
                navController.navigateBack()
            }
        }
    }.build()
}