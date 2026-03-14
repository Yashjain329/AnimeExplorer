package com.example.animeexplorer.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.Room
import android.content.Context
import com.example.animeexplorer.data.database.FavoriteAnimeEntity
import com.example.animeexplorer.data.database.WatchlistAnimeEntity
import com.example.animeexplorer.data.database.FavoriteAnimeDao
import com.example.animeexplorer.data.database.WatchlistAnimeDao

@Database(
    entities = [FavoriteAnimeEntity::class, WatchlistAnimeEntity::class],  // ✅ Added Watchlist
    version = 1,
    exportSchema = false
)
abstract class AnimeDatabase : RoomDatabase() {
    abstract fun favoriteAnimeDao(): FavoriteAnimeDao
    abstract fun watchlistAnimeDao(): WatchlistAnimeDao

    companion object {
        const val DATABASE_NAME = "anime_explorer_db"

        @Volatile
        private var INSTANCE: AnimeDatabase? = null

        fun getDatabase(context: Context): AnimeDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AnimeDatabase::class.java,
                    DATABASE_NAME
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
