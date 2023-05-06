package repository

interface GoogleDriveRepository {
    suspend fun greeting(): String
}