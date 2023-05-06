package repository

import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*

class DefaultGoogleDriveRepository(private val client: HttpClient): GoogleDriveRepository {
    override suspend fun greeting(): String {
        val response = client.get("https://ktor.io/docs/")
        return response.bodyAsText()
    }
}