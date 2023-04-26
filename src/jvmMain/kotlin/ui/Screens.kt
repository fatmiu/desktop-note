package ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector

/**
 * Screens
 */
enum class MainScreen(
    val label: String,
    val icon: ImageVector
) {
    NoteMainScreen(
        label = "Home",
        icon = Icons.Filled.List
    ),
    PhotoMainScreen(
        label = "Photo",
        icon = Icons.Filled.AccountBox
    ),
    SettingsMainScreen(
        label = "Settings",
        icon = Icons.Filled.Settings
    )
}