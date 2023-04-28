package ui.note

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
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
            NoteScreen() {
                navController.navigate(Routes.NOTE_EDIT)
            }
        }
        composable(Routes.NOTE_EDIT) {
            NoteEditScreen() {
                navController.navigateBack()
            }
        }
    }.build()
}