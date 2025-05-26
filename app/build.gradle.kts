plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("org.jetbrains.kotlin.plugin.compose") version "2.1.20" // this version matches your Kotlin version
    id ("kotlin-kapt")
    id("com.google.dagger.hilt.android")
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
    }
    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            buildConfigField("String", "BASE_URL", "\"http://94.228.125.136:8080/\"")
        }

        debug{
            buildConfigField("String", "BASE_URL", "\"http://94.228.125.136:8080/\"")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    // Основные зависимости
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
    kapt(libs.androidx.hilt.compiler) // Компилятор Hilt
    implementation(libs.androidx.lifecycle.livedata.ktx) // LiveData
    implementation(libs.androidx.lifecycle.viewmodel.ktx) // ViewModel

    // Дополнительные зависимости
    implementation(libs.dagger) // Dagger
    kapt(libs.dagger.compiler) // Компилятор Dagger
}
