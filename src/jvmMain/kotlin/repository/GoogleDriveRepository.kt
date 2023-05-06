package repository

import java.io.File

interface GoogleDriveRepository {
    suspend fun greeting(): String

    suspend fun upload(file: File): String?
}