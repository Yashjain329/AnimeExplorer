package com.example.animeexplorer.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey


 //Room Entity for storing favorite anime locally
 //Stored in "favorite_anime" table
@Entity(tableName = "favorite_anime")
data class FavoriteAnimeEntity(
    @PrimaryKey val malId: Int,
    val title: String,
    val imageUrl: String,
    val score: Double,
    val posterUrl: String,
    val synopsis: String? = null,
    val episodes: Int? = null,
    val genres: String? = null,  // Comma-separated genre names
    val status: String? = null,
    val rating: String? = null,
    val addedAt: Long = System.currentTimeMillis()
)


 //Room Entity for watchlist
@Entity(tableName = "watchlist_anime")
data class WatchlistAnimeEntity(
    @PrimaryKey val malId: Int,
    val title: String,
    val imageUrl: String,
    val score: Double,
    val posterUrl: String,
    val addedAt: Long = System.currentTimeMillis(),
    val status: String? = null
)