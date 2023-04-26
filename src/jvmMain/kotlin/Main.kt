import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.miumiu.Database
import commiumiusqldelighthockeydata.HockeyPlayer
import commiumiusqldelighthockeydata.PlayerQueries

@Composable
@Preview
fun App() {
    var text by remember { mutableStateOf("Hello, World!") }

    MaterialTheme {
        Button(onClick = {
            text = "Hello, Desktop!"
        }) {
            Text(text)
        }
    }
}

fun main() = application {
    val database = Database(DriverFactory().createDriver())

    val playerQueries: PlayerQueries = database.playerQueries

    println(playerQueries.selectAll().executeAsList())

    playerQueries.insert(player_number = 10, full_name = "Corey Perry")
    println(playerQueries.selectAll().executeAsList())

    val player = HockeyPlayer(10, "Ronald McDonald")
    playerQueries.insertFullPlayerObject(player)

    Window(onCloseRequest = ::exitApplication) {
        App()
    }
}
