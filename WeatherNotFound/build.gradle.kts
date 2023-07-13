import java.util.Properties

val propertiesConfigurationFileName = "weatherNotFound.properties"
val propertiesApiKeyName = "OpenWeatherApiKey"
val propertiesResponseLanguage = "OpenWeatherResponseLanguage"
val propertiesResponseUnit = "OpenWeatherResponseUnit"

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp") version "1.8.10-1.0.9"
    id("maven-publish")
}

android {
    namespace = "me.learning.weathernotfound"
    compileSdk = 33

    defaultConfig {
        minSdk = 21
        consumerProguardFiles("consumer-rules.pro")

        printInfoLog("Reading weatherNotFound.properties file...")

        buildConfigField(
            type = "String",
            name = propertiesApiKeyName,
            value = "\"${getPropertyFromPropertiesFile<String>(propertiesApiKeyName)}\""
        )

        buildConfigField(
            type = "String",
            name = propertiesResponseLanguage,
            value = "\"${getPropertyFromPropertiesFile<String>(propertiesResponseLanguage)}\""
        )

        buildConfigField(
            type = "String",
            name = propertiesResponseUnit,
            value = "\"${getPropertyFromPropertiesFile<String>(propertiesResponseUnit)}\""
        )

        printInfoLog("Reading was finished and everything is ready to go!")

        // Export current version database schema
        javaCompileOptions {
            annotationProcessorOptions {
                arguments["room.schemaLocation"] = "$projectDir/schemas"
            }
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }

    buildFeatures {
        buildConfig = true
    }
}

dependencies {
    // Core Dependencies
    val appCompatVersion = "1.6.1"

    implementation("androidx.appcompat:appcompat:$appCompatVersion")

    // Network Dependencies
    val gsonConverterVersion = "2.10.1"
    val okhttpClientVersion = "4.11.0"

    implementation("com.google.code.gson:gson:$gsonConverterVersion")
    implementation("com.squareup.okhttp3:okhttp:$okhttpClientVersion")
    api("com.squareup.okhttp3:logging-interceptor:$okhttpClientVersion")

    // Database Dependencies
    val roomVersion = "2.5.1"

    implementation("androidx.room:room-runtime:$roomVersion")
    implementation("androidx.room:room-ktx:$roomVersion")
    ksp("androidx.room:room-compiler:$roomVersion")

    // Kotlin Coroutines Dependencies
    val coroutinesVersion = "1.6.4"
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutinesVersion")
}

publishing {
    publications {
        register<MavenPublication>("release") {
            groupId = "com.personal"
            artifactId = "WeatherNotFound"
            version = "1.0.0"

            afterEvaluate {
                from(components["release"])
            }
        }
    }
}

fun <T> getPropertyFromPropertiesFile(key: String): T {
    val result = getPropertiesFile()[key]
        ?: throw IllegalArgumentException(
            "WeatherNotFound couldn't find $key in $propertiesConfigurationFileName file.\n" +
                    "Please read the documentation and make sure that you followed the steps correctly!"
        )
    return result as T
}

fun getPropertiesFile(): Properties {
    val propertiesFile = File(propertiesConfigurationFileName)
    if (propertiesFile.exists() && propertiesFile.isFile) {
        val file = Properties().apply {
            load(propertiesFile.inputStream())
        }
        return file
    } else {
        throw IllegalStateException(
            "WeatherNotFound couldn't find ($propertiesConfigurationFileName) file!\n" +
                    "Please read the documentation and make sure that you followed the steps correctly!"
        )
    }
}

fun printInfoLog(message: String) {
    project.logger.lifecycle("WeatherNotFound: $message")
}