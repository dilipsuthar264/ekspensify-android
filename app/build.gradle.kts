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
    namespace = "com.ekspensify.app"
    compileSdk = 35

    val properties = Properties()
    properties.load(project.rootProject.file("local.properties").inputStream())
    defaultConfig {
        applicationId = "com.ekspensify.app"
        minSdk = 25
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"
        multiDexEnabled = true

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }

        buildConfigField("String", "CLIENT_ID", properties.getProperty("CLIENT_ID"))
        buildConfigField("String", "ONESIGNAL_APP_ID", properties.getProperty("ONESIGNAL_APP_ID"))
    }

    signingConfigs {
        create("release") {
            storeFile = file(properties.getProperty("RELEASE_STORE_FILE"))
            storePassword = properties.getProperty("RELEASE_STORE_PASSWORD")
            keyAlias = properties.getProperty("RELEASE_KEY_ALIAS")
            keyPassword = properties.getProperty("RELEASE_KEY_PASSWORD")
        }
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
            isMinifyEnabled = true
            isShrinkResources = true
            isDebuggable = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("release")
//            signingConfig = signingConfigs.getByName("debug")
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
    implementation(libs.androidx.hilt.common)
    implementation(libs.androidx.hilt.work)
//    testImplementation(libs.junit)
//    androidTestImplementation(libs.androidx.junit)
//    androidTestImplementation(libs.androidx.espresso.core)
//    androidTestImplementation(platform(libs.androidx.compose.bom))
//    androidTestImplementation(libs.androidx.ui.test.junit4)
//    debugImplementation(libs.androidx.ui.tooling)
//    debugImplementation(libs.androidx.ui.test.manifest)

    /**
     * FOR DAGGER HILT
     */

    implementation(libs.hilt.android)
    implementation(libs.androidx.compose.material)
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
    implementation(libs.androidx.animation.graphics)

    implementation(libs.ui)

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
    implementation(libs.androidx.credentials)
    implementation(libs.androidx.credentials.play.services.auth)
    implementation(libs.googleid)

    implementation(libs.androidx.appcompat)

    implementation(libs.coil.compose)
    implementation(libs.coil.gif)
    implementation(libs.lottie.compose)


    implementation(libs.androidx.animation)

    implementation(libs.accompanist.permissions)


    implementation(libs.androidx.paging.runtime.ktx)
    implementation(libs.androidx.paging.runtime)
    implementation(libs.androidx.paging.compose)

    implementation(libs.wheelpickercompose)

    // for LocalDateTime time api uses for below 26 sdk
    coreLibraryDesugaring(libs.desugar.jdk.libs)

    implementation(libs.androidx.constraintlayout.compose)


    /**
     * one signal
     */
    implementation(libs.onesignal)


    // WorkManager for background tasks
    implementation(libs.androidx.work.runtime.ktx)

    /**
     * ROOM DATABASE
     */
    implementation(libs.androidx.room.runtime)
    annotationProcessor(libs.androidx.room.compiler)
    //noinspection KaptUsageInsteadOfKsp
    kapt(libs.androidx.room.compiler)
    implementation(libs.androidx.room.ktx)
    implementation(libs.androidx.room.paging)


}