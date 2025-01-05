import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    kotlin("plugin.serialization") version "2.0.20"

    id("kotlin-parcelize")

    //for hilt
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")

    id("com.google.gms.google-services")

    id("com.google.firebase.firebase-perf")

    id("com.google.firebase.crashlytics")
}




android {
    namespace = "com.memeusix.budgetbuddy"
    compileSdk = 34

    val properties = Properties()
    properties.load(project.rootProject.file("local.properties").inputStream())
    defaultConfig {
        applicationId = "com.memeusix.budgetbuddy"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }

        buildConfigField("String", "CLIENT_ID", properties.getProperty("CLIENT_ID"))
        buildConfigField("String", "ONESIGNAL_APP_ID", properties.getProperty("ONESIGNAL_APP_ID"))
    }

    flavorDimensions += "base_url"

    productFlavors {
        create("server") {
            dimension = "base_url"
            buildConfigField("String", "BASE_URL", properties.getProperty("SERVER_HOST_API"))
        }
        create("local") {
            dimension = "base_url"
            buildConfigField("String", "BASE_URL", properties.getProperty("LOCAL_HOST_API"))
        }
    }

    buildTypes {
        debug {
            isDebuggable = true
        }
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("debug")
        }
    }
    compileOptions {
        isCoreLibraryDesugaringEnabled = true
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }


}
// Allow references to generated code
kapt {
    correctErrorTypes = true
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.ui.test.android)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    /**
     * FOR DAGGER HILT
     */

    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)

    /**
     * FOR VIEW MODEL AND LIVE DATA
     */

// ViewModel
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
// ViewModel utilities for Compose
    implementation(libs.androidx.lifecycle.viewmodel.compose)
// LiveData
    implementation(libs.androidx.lifecycle.livedata.ktx)
// Lifecycle utilities for Compose
    implementation(libs.androidx.lifecycle.runtime.compose)

// Saved state module for ViewModel
    implementation(libs.androidx.lifecycle.viewmodel.savedstate)

// Annotation processor
    kapt(libs.androidx.lifecycle.compiler)

    /**
     * Kotlin coroutines
     */
    implementation(libs.kotlinx.coroutines.android)

    /**
     * for Api calling Retrofit
     */
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.okhttp)
    implementation(libs.logging.interceptor)

    /**
     * for Navigation
     */
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.hilt.navigation.compose)


    /**
     * for splash screen
     */
    implementation(libs.androidx.core.splashscreen)


    /**
     * Fire base
     */
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.analytics)
    implementation(libs.firebase.perf)
    implementation(libs.firebase.crashlytics)


    /**
     * Google sign in
     */
    implementation(libs.play.services.auth)

    /**
     *  Serializable for Type safe Navigation
     */
    implementation(libs.kotlinx.serialization.json)

    implementation(libs.accompanist.systemuicontroller)

    /**
     *  for google auth
     */
    implementation("androidx.credentials:credentials:1.3.0")
    implementation("androidx.credentials:credentials-play-services-auth:1.3.0")
    implementation("com.google.android.libraries.identity.googleid:googleid:1.1.0")

    implementation("androidx.appcompat:appcompat:1.7.0")

    implementation("io.coil-kt:coil-compose:2.7.0")


    implementation("androidx.compose.animation:animation:1.7.5")

    implementation("com.google.accompanist:accompanist-permissions:0.34.0")


    implementation("androidx.paging:paging-runtime-ktx:3.3.4")
    implementation("androidx.paging:paging-runtime:3.3.4")
    implementation("androidx.paging:paging-compose:3.3.4")

    implementation("com.github.commandiron:WheelPickerCompose:1.1.11")

    // for LocalDateTime time api uses for below 26 sdk
    coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:2.1.3")

    implementation("androidx.constraintlayout:constraintlayout-compose:1.1.0")


    /**
     * Memory Leak
     */
    debugImplementation("com.squareup.leakcanary:leakcanary-android:2.14")


    /**
     * one signal
     */
    implementation("com.onesignal:OneSignal:[5.0.0, 5.99.99]")


    // Photo View with gesture
    implementation ("io.github.fornewid:photo-compose:1.0.1")
}