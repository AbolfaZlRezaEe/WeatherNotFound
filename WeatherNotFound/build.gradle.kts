import java.util.Properties

val propertiesConfigurationFileName = "weatherNotFound.properties"
val propertiesApiKeyName = "OpenWeatherApiKey"

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp") version "1.8.10-1.0.9"
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
            name = "openWeatherApi",
            value = "\"${getPropertyFromPropertiesFile<String>(propertiesApiKeyName)}\""
        )

        printInfoLog("Reading was finished and everything is ready to go!")
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
    // Network Dependencies
    val gsonConverterVersion = "2.10.1"

    implementation("com.google.code.gson:gson:$gsonConverterVersion")

    // Database Dependencies
    val roomVersion = "2.5.1"

    implementation("androidx.room:room-runtime:$roomVersion")
    ksp("androidx.room:room-compiler:$roomVersion")
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