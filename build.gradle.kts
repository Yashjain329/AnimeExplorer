// Top-level build file where you can add configuration options common to all sub-projects/modules.

plugins {
    id("com.android.application") version "8.13.2" apply false
    id("com.android.library") version "8.13.2" apply false

    // Kotlin – ALL SAME VERSION
    kotlin("android") version "1.9.24" apply false
    kotlin("kapt") version "1.9.24" apply false
    kotlin("plugin.serialization") version "1.9.24" apply false

    // Hilt
    id("com.google.dagger.hilt.android") version "2.51" apply false
}