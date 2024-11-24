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
}




android {
    namespace = "com.memeusix.budgetbuddy"
    compileSdk = 34

    val properties = Properties()

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
        properties.load(project.rootProject.file("local.properties").inputStream())
        buildConfigField("String", "CLIENT_ID", properties.getProperty("CLIENT_ID"))
    }


    buildTypes {
        debug {
            isDebuggable = true
            buildConfigField("String", "BASE_URL", project.property("HOST_API").toString())
        }
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            buildConfigField("String", "BASE_URL", project.property("HOST_API").toString())
            signingConfig = signingConfigs.getByName("debug")
        }
//
//        create("staging") {
//            initWith(getByName("release"))
//            proguardFiles(
//                getDefaultProguardFile("proguard-android-optimize.txt"),
//                "proguard-rules.pro"
//            )
//            buildConfigField("String", "BASE_URL", project.property("HOST_API_IP").toString())
//            signingConfig = signingConfigs.getByName("debug")
//        }
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

    implementation("androidx.appcompat:appcompat:1.3.1")

    implementation("io.coil-kt:coil-compose:2.7.0")

}