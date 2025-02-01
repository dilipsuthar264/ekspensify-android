import org.bouncycastle.oer.its.etsi102941.Url

// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false

    // hilt plugin
    id("com.google.dagger.hilt.android") version "2.50" apply false

    // Firebase
    id("com.google.gms.google-services") version "4.4.2" apply false

    //firebase
    id("com.google.firebase.firebase-perf") version "1.4.2" apply false

    id("com.google.firebase.crashlytics") version "3.0.2" apply false
}
buildscript {
    repositories {
        google()
        maven { url = uri("https://oss.sonatype.org/content/repositories/snapshots/") }
    }
    dependencies{
        classpath (libs.r8)
    }
}