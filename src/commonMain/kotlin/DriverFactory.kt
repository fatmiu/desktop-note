import app.cash.sqldelight.db.SqlDriver
import com.miumiu.Database

expect class DriverFactory {
    fun createDriver(): SqlDriver
}