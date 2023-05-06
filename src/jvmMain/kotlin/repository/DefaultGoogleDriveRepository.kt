package repository

import com.google.api.client.googleapis.json.GoogleJsonResponseException
import com.google.api.client.http.FileContent
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import util.GoogleDriveUtil.getMetadata
import util.GoogleDriveUtil.service


class DefaultGoogleDriveRepository(private val client: HttpClient) : GoogleDriveRepository {
    override suspend fun greeting(): String {
        val response = client.get("https://ktor.io/docs/")
        return response.bodyAsText()
    }

    override suspend fun upload(file: java.io.File): String {
        return try {
            val uploadFile = service
                .files()
                .create(getMetadata(), FileContent("image/jpeg", file))
                .setFields("id")
                .execute()
            uploadFile.id
        } catch (e: GoogleJsonResponseException) {
            System.err.println("Unable to upload file: " + e.details)
            throw e
        }
    }

}