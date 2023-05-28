package repository

import java.io.File

interface GoogleDriveRepository {
    suspend fun greeting(): String

    suspend fun upload(file: File, onSuccess: ((id: String) -> Unit)? = null): String?

    suspend fun getPhotoList(): List<com.google.api.services.drive.model.File>
}