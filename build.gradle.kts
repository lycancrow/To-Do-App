// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.2.1" apply false
    id("org.jetbrains.kotlin.android") version "1.9.22" apply false
}
buildscript {
    repositories {
        google()  // Google's Maven repository
        mavenCentral()  // Maven Central repository
        maven {
            url = uri("https://jitpack.io")
        }
    }

}
