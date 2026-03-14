package com.example.animeexplorer.data.network

import com.example.animeexplorer.data.models.AnimeDetailResponse
import com.example.animeexplorer.data.models.AnimeResponse
import com.example.animeexplorer.data.models.CharacterResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


//Retrofit API Service for Jikan API
//Handles all API calls for anime data
interface AnimeApiService {


    //Get top-rated anime with pagination
    //@param page Page number for pagination
    //@param limit Items per page (max 25)
    @GET("top/anime")
    suspend fun getTopAnime(
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 25
    ): AnimeResponse


    //Search anime by query
    //@param query Search term
    //@param page Page number
    //@param limit Items per page
    @GET("anime")
    suspend fun searchAnime(
        @Query("q") query: String,
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 25
    ): AnimeResponse


    //Get detailed information for single anime
    //@param id MyAnimeList ID of anime
    @GET("anime/{id}")
    suspend fun getAnimeDetails(@Path("id") id: Int): AnimeDetailResponse


    //Get characters for specific anime
    //@param id MyAnimeList ID of anime
    @GET("anime/{id}/characters")
    suspend fun getAnimeCharacters(@Path("id") id: Int): CharacterResponse


    //Get currently airing/trending anime
    //@param page Page number
    //@param limit Items per page
    @GET("top/anime?filter=airing")
    suspend fun getTopAiringAnime(
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 25
    ): AnimeResponse


    //Get current season anime
    //@param page Page number
    //@param limit Items per page
    @GET("seasons/now")
    suspend fun getCurrentSeasonAnime(
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 25
    ): AnimeResponse


    //Get upcoming anime
    //@param page Page number
    //@param limit Items per page
    @GET("seasons/upcoming")
    suspend fun getUpcomingAnime(
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 25
    ): AnimeResponse
}