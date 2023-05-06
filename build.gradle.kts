import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose") version "1.4.0"
    id("app.cash.sqldelight") version "2.0.0-alpha05"
}

group = "com.miumiu"
version = "1.0-SNAPSHOT"

repositories {
    google()
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
}

sqldelight {
    databases {
        create("Database") {
            packageName.set("com.miumiu")
        }
    }
}

kotlin {
    jvm {
        jvmToolchain(11)
        withJava()
    }
    sourceSets {
        val jvmMain by getting {
            val material3Version: String by project
            dependencies {
                implementation(compose.desktop.currentOs)
                implementation("app.cash.sqldelight:sqlite-driver:2.0.0-alpha05")
                implementation("androidx.compose.material3:material3:$material3Version")
                implementation("io.ktor:ktor-client-core:2.3.0")
                implementation("io.ktor:ktor-client-jetty:2.3.0")
                implementation("io.ktor:ktor-client-content-negotiation:2.3.0")
                implementation("io.ktor:ktor-serialization-kotlinx-json:2.3.0")
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")
                implementation("com.google.api-client:google-api-client:2.0.0")
                implementation("com.google.auth:google-auth-library-oauth2-http:1.3.0")
                implementation("com.google.oauth-client:google-oauth-client-jetty:1.34.1")
                implementation("com.google.apis:google-api-services-drive:v3-rev20220815-2.0.0")
            }
        }
        val jvmTest by getting
    }
}

compose.desktop {
    application {
        mainClass = "MainKt"
        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb, TargetFormat.Exe)
            packageName = "note"
            packageVersion = "1.0.0"
            modules("java.sql")
        }
    }
}
