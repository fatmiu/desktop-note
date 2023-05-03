@file:OptIn(ExperimentalMaterialApi::class)

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