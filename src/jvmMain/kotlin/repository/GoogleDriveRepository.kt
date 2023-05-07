package repository

import java.io.File

interface GoogleDriveRepository {
    suspend fun greeting(): String

    suspend fun upload(file: File): String?

    suspend fun getPhotoList(): List<com.google.api.services.drive.model.File>
}