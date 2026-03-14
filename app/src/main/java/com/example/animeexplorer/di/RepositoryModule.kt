package com.example.animeexplorer.di

import com.example.animeexplorer.data.repository.AnimeRepository
import com.example.animeexplorer.data.network.AnimeApiService
import com.example.animeexplorer.data.database.FavoriteAnimeDao
import com.example.animeexplorer.data.database.WatchlistAnimeDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


//Hilt Module for Repository dependency injection
@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideAnimeRepository(
        apiService: AnimeApiService,
        favoriteDao: FavoriteAnimeDao,
        watchlistDao: WatchlistAnimeDao
    ): AnimeRepository {
        return AnimeRepository(apiService, favoriteDao, watchlistDao)
    }
}