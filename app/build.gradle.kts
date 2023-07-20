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
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
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