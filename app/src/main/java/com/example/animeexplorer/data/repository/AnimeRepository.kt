package com.example.animeexplorer.data.repository

import com.example.animeexplorer.data.database.FavoriteAnimeDao
import com.example.animeexplorer.data.database.FavoriteAnimeEntity
import com.example.animeexplorer.data.database.WatchlistAnimeDao
import com.example.animeexplorer.data.models.Anime
import com.example.animeexplorer.data.database.WatchlistAnimeEntity
import com.example.animeexplorer.data.models.Character
import com.example.animeexplorer.data.network.AnimeApiService
import kotlinx.coroutines.flow.Flow
import kotlin.Result
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AnimeRepository @Inject constructor(
    private val apiService: AnimeApiService,
    private val favoriteDao: FavoriteAnimeDao,
    private val watchlistDao: WatchlistAnimeDao
) {

    // ==================== Network Calls (ONLY EXISTING API ENDPOINTS) ====================

    suspend fun getTopAnime(page: Int): Result<List<Anime>> = try {
        val response = apiService.getTopAnime(page = page)
        Result.success(response.data)
    } catch (e: Exception) {
        Result.failure(e)
    }

    suspend fun searchAnime(query: String, page: Int = 1): Result<List<Anime>> = try {
        val response = apiService.searchAnime(query, page = page)
        Result.success(response.data)
    } catch (e: Exception) {
        Result.failure(e)
    }

    suspend fun getAnimeDetails(id: Int): Result<Anime> = try {
        val response = apiService.getAnimeDetails(id)
        Result.success(response.data)
    } catch (e: Exception) {
        Result.failure(e)
    }

    suspend fun getAnimeCharacters(id: Int): Result<List<Character>> = try {
        val response = apiService.getAnimeCharacters(id)
        Result.success(response.data)
    } catch (e: Exception) {
        Result.failure(e)
    }

    // ==================== Favorite Operations ====================

    suspend fun addFavorite(anime: Anime) {
        val entity = anime.toFavoriteEntity()
        favoriteDao.insertFavorite(entity)
    }

    suspend fun removeFavorite(id: Int) {
        favoriteDao.deleteFavoriteById(id)
    }

    // Add these after getAnimeCharacters()
    suspend fun getTrendingAnime(page: Int = 1): Result<List<Anime>> = try {
        val response = apiService.getTopAiringAnime(page = page)  // Use existing endpoint
        Result.success(response.data)
    } catch (e: Exception) {
        Result.failure(e)
    }

    suspend fun getUpcomingAnime(page: Int = 1): Result<List<Anime>> = try {
        val response = apiService.getUpcomingAnime(page = page)
        Result.success(response.data)
    } catch (e: Exception) {
        Result.failure(e)
    }

    suspend fun getCurrentSeasonAnime(page: Int = 1): Result<List<Anime>> = try {
        val response = apiService.getCurrentSeasonAnime(page = page)
        Result.success(response.data)
    } catch (e: Exception) {
        Result.failure(e)
    }

    // Add after Favorite operations
    suspend fun addToWatchlist(anime: Anime) {
        val entity = anime.toWatchlistEntity()
        watchlistDao.insertWatchlist(entity)
    }

    suspend fun removeFromWatchlist(id: Int) {
        watchlistDao.deleteWatchlistById(id)
    }

    suspend fun isInWatchlist(id: Int): Boolean =
        watchlistDao.isInWatchlist(id) > 0

    // Add extension function
    fun Anime.toWatchlistEntity(): WatchlistAnimeEntity {
        val imageUrl = images.jpg?.imageUrl ?: images.webp?.imageUrl ?: ""
        val posterUrl = images.jpg?.largeImageUrl ?: images.webp?.largeImageUrl ?: imageUrl

        return WatchlistAnimeEntity(
            malId = malId,
            title = title,
            imageUrl = imageUrl,
            score = score ?: 0.0,
            posterUrl = posterUrl,
            addedAt = System.currentTimeMillis(),
            status = status
        )
    }


    fun getAllFavorites(): Flow<List<FavoriteAnimeEntity>> =
        favoriteDao.getAllFavorites()

    suspend fun isFavorite(id: Int): Boolean =
        favoriteDao.isFavorite(id) > 0

    // ==================== Extension Functions ====================

    fun Anime.toFavoriteEntity(): FavoriteAnimeEntity {
        val genreNames = genres.joinToString(", ") { it.name }
        val imageUrl = images.jpg?.imageUrl ?: images.webp?.imageUrl ?: ""
        val posterUrl = images.jpg?.largeImageUrl ?: images.webp?.largeImageUrl ?: imageUrl

        return FavoriteAnimeEntity(
            malId = malId,
            title = title,
            imageUrl = imageUrl,
            score = score ?: 0.0,
            posterUrl = posterUrl,
            synopsis = synopsis ?: "",
            episodes = episodes,
            genres = genreNames.ifBlank { null },
            status = status,
            rating = rating
        )
    }
}