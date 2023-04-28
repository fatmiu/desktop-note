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
    NavigationHost(navController) {
        composable(Routes.NOTE) {
            val viewModel = noteViewModel
            viewModel.refresh("2023-04-29")
            NoteScreen(
                state = viewModel.state,
                onEvent = { event ->
                    when (event) {
                        NoteEvent.onEdit -> navController.navigate(Routes.NOTE_EDIT)
                    }
                }
            )
        }
        composable(Routes.NOTE_EDIT) {
            NoteEditScreen() {
                navController.navigateBack()
            }
        }
    }.build()
}