package repository

import com.google.api.client.googleapis.json.GoogleJsonResponseException
import com.google.api.client.http.FileContent
import com.google.api.services.drive.model.File
import di.AppModule.service
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import util.GoogleDriveUtil.getMetadata
import util.GoogleDriveUtil.getParent


class DefaultGoogleDriveRepository(private val client: HttpClient) : GoogleDriveRepository {
    override suspend fun greeting(): String {
        val response = client.get("https://ktor.io/docs/")
        return response.bodyAsText()
    }

    override suspend fun upload(file: java.io.File, onSuccess: ((id: String) -> Unit)?): String? {
        return try {
            val uploadFile = service
                .files()
                .create(getMetadata(), FileContent("image/jpeg", file))
                .setFields("id")
                .execute()
            onSuccess?.invoke(uploadFile.id)
            uploadFile.id
        } catch (e: GoogleJsonResponseException) {
            System.err.println("Unable to upload file: " + e.details)
            throw e
        }
    }

    override suspend fun getPhotoList(): List<File> {
        val files: MutableList<File> = ArrayList()

        var pageToken: String? = null
        do {
            val result = service.files().list()
                .setQ("'${getParent()}' in parents and mimeType='image/jpeg'")
                .setSpaces("drive")
                .setFields("nextPageToken, files(id, name, thumbnailLink, webContentLink)")
                .setPageToken(pageToken)
                .execute()
            for (file in result.files) {
                println("Found file: ${file.webContentLink.substringBefore("&")} ${file.name} (${file.id})")
            }
            files.addAll(result.files)
            pageToken = result.nextPageToken
        } while (pageToken != null)

        return files
    }

}