import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import com.miumiu.Database
import java.io.File

actual class DriverFactory {
    actual fun createDriver(): SqlDriver {

        val dbPath = File(System.getProperty("java.io.tmpdir"), "noteDb")
        val jdbcUrl = "jdbc:sqlite:${dbPath.absolutePath}"
        val db = File(dbPath.absolutePath)
        val driver: SqlDriver = JdbcSqliteDriver(jdbcUrl)
        if(!isFileExists(db)) {
            Database.Schema.create(driver)
        }

        return driver
    }
}

fun isFileExists(file: File): Boolean {
    return file.exists() && !file.isDirectory
}