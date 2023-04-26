import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import ui.MainScreen
import ui.note.NoteMainScreen
import ui.photo.PhotoMainScreen
import ui.setting.SettingMainScreen
import util.NavController
import util.NavigationHost
import util.composable
import util.rememberNavController


fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        mainScreen()
    }
}

@Composable
fun mainScreen() {
    val screens = MainScreen.values().toList()
    val navController by rememberNavController(MainScreen.NoteMainScreen.name)
    val currentScreen by remember {
        navController.currentScreen
    }

    MaterialTheme {
        Surface(
            modifier = Modifier.background(color = MaterialTheme.colors.background)
        ) {
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                // I have used navigation rail to show how it works
                // You can use your own navbar
                NavigationRail(
                    modifier = Modifier.align(Alignment.CenterStart).fillMaxHeight()
                ) {
                    screens.forEach {
                        NavigationRailItem(
                            selected = currentScreen == it.name,
                            icon = {
                                Icon(
                                    imageVector = it.icon,
                                    contentDescription = it.label
                                )
                            },
                            label = {
                                Text(it.label)
                            },
                            alwaysShowLabel = false,
                            onClick = {
                                navController.navigate(it.name)
                            }
                        )
                    }
                }

                Box(
                    modifier = Modifier.fillMaxHeight()
                ) {
                    MainNavigationHost(navController = navController)
                }
            }
        }
    }
}

@Composable
fun MainNavigationHost(
    navController: NavController
) {
    NavigationHost(navController) {
        composable(MainScreen.NoteMainScreen.name) {
            NoteMainScreen()
        }
        composable(MainScreen.PhotoMainScreen.name) {
            PhotoMainScreen()
        }
        composable(MainScreen.SettingsMainScreen.name) {
            SettingMainScreen()
        }
    }.build()
}