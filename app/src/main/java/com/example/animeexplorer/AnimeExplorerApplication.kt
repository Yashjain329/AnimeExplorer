package com.example.animeexplorer

import android.app.Application
import dagger.hilt.android.HiltAndroidApp


//Application class for Hilt dependency injection
@HiltAndroidApp
class AnimeExplorerApplication : Application() {

    override fun onCreate() {
        super.onCreate()
    }
}