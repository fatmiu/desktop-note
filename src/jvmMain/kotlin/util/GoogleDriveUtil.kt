package util

import com.google.api.client.http.HttpRequestInitializer
import com.google.api.services.drive.DriveScopes
import com.google.api.services.drive.model.File
import com.google.auth.http.HttpCredentialsAdapter
import com.google.auth.oauth2.GoogleCredentials
import java.io.FileInputStream
import java.time.LocalDateTime
import java.util.*

object GoogleDriveUtil {
    fun getRequestInitializer(): HttpRequestInitializer {
        val credentialPath = object {}.javaClass.classLoader.getResource("credentials.json")?.path?.substring(1)
        val credential = credentialPath?.let { java.io.File(it) }
        val inputStream = credential?.let { FileInputStream(it) }
        val credentials = GoogleCredentials.fromStream(inputStream)
            .createScoped(listOf(DriveScopes.DRIVE_FILE))
        return HttpCredentialsAdapter(credentials)
    }

    fun getMetadata(): File {
        return File().apply {
            name = "${LocalDateTime.now().toLocalDate()}_${UUID.randomUUID()}"
            parents = Collections.singletonList(getParent())
        }
    }

    fun getParent(): String {
        // TODO: set by user
        return "1totSLiwuza1V0bUlxYHFioZco1K419yT"
    }
}