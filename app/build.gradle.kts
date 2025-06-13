plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("org.jetbrains.kotlin.plugin.compose") version "2.1.20" // эта версия соответствует вашей версии Kotlin
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
    id("kotlin-parcelize")
}

android {
    namespace = "rus.one.app"
    compileSdk = 35

    defaultConfig {
        applicationId = "rus.one.app"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildFeatures {
        viewBinding = true
        buildConfig = true
        compose = true // Если вы используете Jetpack Compose
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            buildConfigField("String", "BASE_URL", "\"http://94.228.125.136:8080/\"")
            buildConfigField("String", "API_KEY", "\"${project.findProperty("API_KEY") ?: "c1378193-bc0e-42c8-a502-b8d66d189617"}\"")
        }

        debug {
            buildConfigField("String", "BASE_URL", "\"http://94.228.125.136:8080/\"")
            buildConfigField("String", "API_KEY", "\"${project.findProperty("API_KEY") ?: "c1378193-bc0e-42c8-a502-b8d66d189617"}\"")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    // Основные зависимости
    implementation("com.github.bumptech.glide:glide:4.16.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")
    implementation(libs.androidx.room.runtime)   // Основная библиотека Room
    implementation(libs.androidx.room.ktx)
    implementation(libs.play.services.identity.credentials)       // KTX для Room
    kapt(libs.androidx.room.compiler)            // Компилятор для Room
    implementation("androidx.room:room-runtime:2.7.1")
    implementation("androidx.room:room-ktx:2.7.1")
    kapt("androidx.room:room-compiler:2.7.1")
    // Другие зависимости...
    implementation("io.coil-kt:coil-compose:2.4.0")
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom)) // BOM для Compose
    implementation(libs.androidx.ui) // Основной UI
    implementation(libs.androidx.ui.graphics) // Графика
    implementation(libs.androidx.ui.tooling.preview) // Предварительный просмотр
    implementation(libs.androidx.material3) // Material3
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.activity.ktx)
    implementation(libs.androidx.lifecycle.livedata.ktx) // LiveData
    implementation(libs.androidx.lifecycle.viewmodel.ktx) // ViewModel

    // Базовый ExoPlayer (Media3) — плеер и ядро
    implementation("androidx.media3:media3-exoplayer:1.7.1")

    // UI-компоненты Media3 (PlayerView и др.)
    implementation("androidx.media3:media3-ui:1.7.1")

    // Compose-совместимые UI-компоненты (экспериментальные)
    implementation("androidx.media3:media3-session:1.7.1")
    implementation("androidx.media3:media3-exoplayer-dash:1.7.1") // если нужен DASH
    implementation("androidx.media3:media3-exoplayer-hls:1.7.1")  // если нужен HLS
    implementation("com.google.android.exoplayer:exoplayer-core:2.19.1")
    implementation("com.google.android.exoplayer:exoplayer-ui:2.19.1")


    // Тестирование
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom)) // BOM для тестов
    androidTestImplementation(libs.androidx.ui.test.junit4) // Тесты для Compose
    debugImplementation(libs.androidx.ui.tooling) // Для отладки

    // OkHttp и логирование
    implementation(libs.okhttp)
    implementation(libs.logging.interceptor)

    // Hilt и ViewModel
    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)
    implementation(libs.androidx.hilt.navigation.compose) // Навигация с Hilt
}
