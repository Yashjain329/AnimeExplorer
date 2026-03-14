package com.example.animeexplorer.di

import android.app.Application
import com.example.animeexplorer.data.database.AnimeDatabase
import com.example.animeexplorer.data.database.FavoriteAnimeDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import com.example.animeexplorer.data.database.WatchlistAnimeDao

//Hilt Module for Database dependency injection
@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAnimeDatabase(app: Application): AnimeDatabase {
        return AnimeDatabase.getDatabase(app)
    }

    @Provides
    fun provideFavoriteAnimeDao(db: AnimeDatabase): FavoriteAnimeDao {
        return db.favoriteAnimeDao()
    }

    // ADD THIS LINE:
    @Provides
    fun provideWatchlistAnimeDao(db: AnimeDatabase): WatchlistAnimeDao {
        return db.watchlistAnimeDao()
    }
}