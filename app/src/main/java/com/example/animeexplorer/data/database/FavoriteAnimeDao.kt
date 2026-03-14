package com.example.animeexplorer.data.database

import androidx.room.*
import kotlinx.coroutines.flow.Flow


 //Data Access Object for Favorite Anime
 //Handles all database operations for favorites
@Dao
interface FavoriteAnimeDao {


     //Insert or replace favorite anime
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(anime: FavoriteAnimeEntity)


     //Delete favorite anime
    @Delete
    suspend fun deleteFavorite(anime: FavoriteAnimeEntity)


     //Delete favorite by ID
    @Query("DELETE FROM favorite_anime WHERE malId = :id")
    suspend fun deleteFavoriteById(id: Int)


     //Get all favorites as Flow (reactive updates)
    @Query("SELECT * FROM favorite_anime ORDER BY addedAt DESC")
    fun getAllFavorites(): Flow<List<FavoriteAnimeEntity>>


     //Get favorite by ID
    @Query("SELECT * FROM favorite_anime WHERE malId = :id")
    suspend fun getFavoriteById(id: Int): FavoriteAnimeEntity?


     //Check if anime is favorite
    @Query("SELECT COUNT(*) FROM favorite_anime WHERE malId = :id")
    suspend fun isFavorite(id: Int): Int
}


 //Data Access Object for Watchlist
@Dao
interface WatchlistAnimeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWatchlist(anime: WatchlistAnimeEntity)

    @Delete
    suspend fun deleteWatchlist(anime: WatchlistAnimeEntity)

    @Query("DELETE FROM watchlist_anime WHERE malId = :id")
    suspend fun deleteWatchlistById(id: Int)

    @Query("SELECT * FROM watchlist_anime ORDER BY addedAt DESC")
    fun getAllWatchlist(): Flow<List<WatchlistAnimeEntity>>

    @Query("SELECT * FROM watchlist_anime WHERE malId = :id")
    suspend fun getWatchlistById(id: Int): WatchlistAnimeEntity?

    @Query("SELECT COUNT(*) FROM watchlist_anime WHERE malId = :id")
    suspend fun isInWatchlist(id: Int): Int
}