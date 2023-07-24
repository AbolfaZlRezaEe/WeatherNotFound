import java.util.Properties

// BuildConfig Keys
val propertiesConfigurationFileName = "weatherNotFound.properties"
val propertiesApiKeyName = "OpenWeatherApiKey"
val propertiesResponseLanguage = "OpenWeatherResponseLanguage"
val propertiesResponseUnit = "OpenWeatherResponseUnit"
val propertiesResponseFormat = "OpenWeatherResponseFormat"
val propertiesDatabaseExportSchemaEnabled = "DatabaseSchemaShouldEnable"

// BuildConfig Default Values

val propertiesDatabaseExportSchemaEnabledDefaultValue = "false"
plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "me.learning.app"
    compileSdk = 33

    defaultConfig {
        applicationId = "me.learning.app"
        minSdk = 21
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

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

        buildConfigField(
            type = "String",
            name = propertiesResponseFormat,
            value = "\"${getPropertyFromPropertiesFile<String>(propertiesResponseFormat)}\""
        )

        buildConfigField(
            type = "boolean",
            name = propertiesDatabaseExportSchemaEnabled,
            value = getPropertyFromPropertiesFile<String>(propertiesDatabaseExportSchemaEnabled) ?: propertiesDatabaseExportSchemaEnabledDefaultValue
        )

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

    lint.checkReleaseBuilds = false
}

dependencies {
    val coreKTXVersion = "1.10.1"
    val appCompatVersion = "1.6.1"
    val materialVersion = "1.9.0"
    val constraintLayoutVersion = "2.1.4"

    implementation("androidx.core:core-ktx:$coreKTXVersion")
    implementation("androidx.appcompat:appcompat:$appCompatVersion")
    implementation("com.google.android.material:material:$materialVersion")
    implementation("androidx.constraintlayout:constraintlayout:$constraintLayoutVersion")


    implementation(project(":WeatherNotFound"))
}

fun <T> getPropertyFromPropertiesFile(key: String): T? {
    if (!propertiesFileExist()) return null
    return getPropertiesFile()!![key] as T
}

fun getPropertiesFile(): Properties? {
    val propertiesFile = File(propertiesConfigurationFileName)
    if (propertiesFile.exists() && propertiesFile.isFile) {
        val file = Properties().apply {
            load(propertiesFile.inputStream())
        }
        return file
    }
    return null
}

fun propertiesFileExist(): Boolean {
    val propertiesFile = File(propertiesConfigurationFileName)
    return propertiesFile.exists() && propertiesFile.isFile
}

fun printInfoLog(message: String) {
    project.logger.lifecycle("WeatherNotFound: $message")
}