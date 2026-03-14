package com.example.animeexplorer.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.animeexplorer.data.models.Anime
import com.example.animeexplorer.data.repository.AnimeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

// UI State sealed class for reactive state management
sealed class UiState<out T> {
    object Loading : UiState<Nothing>()
    object Empty : UiState<Nothing>()
    data class Success<T>(val data: T) : UiState<T>()
    data class Error(val message: String) : UiState<Nothing>()
}

// ViewModel for Anime listing screens
@HiltViewModel
class AnimeViewModel @Inject constructor(
    private val repository: AnimeRepository
) : ViewModel() {

    // State flows for UI
    private val _topAnimeState = MutableStateFlow<UiState<List<Anime>>>(UiState.Loading)
    val topAnimeState: StateFlow<UiState<List<Anime>>> = _topAnimeState.asStateFlow()

    private val _searchState = MutableStateFlow<UiState<List<Anime>>>(UiState.Empty)
    val searchState: StateFlow<UiState<List<Anime>>> = _searchState.asStateFlow()

    private val _trendingState = MutableStateFlow<UiState<List<Anime>>>(UiState.Loading)
    val trendingState: StateFlow<UiState<List<Anime>>> = _trendingState.asStateFlow()

    private val _upcomingState = MutableStateFlow<UiState<List<Anime>>>(UiState.Loading)
    val upcomingState: StateFlow<UiState<List<Anime>>> = _upcomingState.asStateFlow()

    private val _currentSeasonState = MutableStateFlow<UiState<List<Anime>>>(UiState.Loading)
    val currentSeasonState: StateFlow<UiState<List<Anime>>> = _currentSeasonState.asStateFlow()

    private val _favoriteIds = MutableStateFlow<Set<Int>>(emptySet())
    val favoriteIds: StateFlow<Set<Int>> = _favoriteIds.asStateFlow()

    private val _currentPage = MutableStateFlow(1)
    val currentPage: StateFlow<Int> = _currentPage.asStateFlow()

    // Pagination
    private var currentListType = ListType.TOP

    enum class ListType {
        TOP, TRENDING, UPCOMING, SEASON
    }

    init {
        loadHomeData()
        loadFavorites()
    }

    // ✅ PUBLIC for pull-to-refresh
    fun loadHomeData() {
        viewModelScope.launch {
            // Top anime first
            _topAnimeState.value = UiState.Loading
            val topResult = repository.getTopAnime(1)
            _topAnimeState.value = mapResult(topResult, "Top anime")
            delay(3500)

            // Trending
            _trendingState.value = UiState.Loading
            val trendingResult = repository.getTrendingAnime(1)
            _trendingState.value = mapResult(trendingResult, "Trending")
            delay(3500)

            // Upcoming
            _upcomingState.value = UiState.Loading
            val upcomingResult = repository.getUpcomingAnime(1)
            _upcomingState.value = mapResult(upcomingResult, "Upcoming")
            delay(3500)

            // Current season
            _currentSeasonState.value = UiState.Loading
            val seasonResult = repository.getCurrentSeasonAnime(1)
            _currentSeasonState.value = mapResult(seasonResult, "Season")
        }
    }

    // Load top-rated anime (PAGINATION SUPPORT)
    fun loadTopAnime(page: Int = 1) {
        viewModelScope.launch {
            _topAnimeState.value = UiState.Loading
            currentListType = ListType.TOP
            _currentPage.value = page

            val result = repository.getTopAnime(page)
            _topAnimeState.value = if (result.isSuccess) {
                val animes = result.getOrNull() ?: emptyList()
                if (animes.isEmpty()) {
                    UiState.Empty
                } else {
                    UiState.Success(animes)
                }
            } else {
                UiState.Error(result.exceptionOrNull()?.message ?: "Failed to load top anime")
            }
        }
    }

    private fun mapResult(result: Result<List<Anime>>, type: String): UiState<List<Anime>> {
        return if (result.isSuccess) {
            val data = result.getOrNull() ?: emptyList()
            if (data.isEmpty()) UiState.Empty else UiState.Success(data)
        } else {
            Log.e("AnimeViewModel", "$type failed: ${result.exceptionOrNull()?.message}")
            UiState.Error(result.exceptionOrNull()?.message ?: "$type failed")
        }
    }

    // Search anime by query
    fun searchAnime(query: String, page: Int = 1) {
        if (query.isBlank()) {
            _searchState.value = UiState.Empty
            return
        }

        viewModelScope.launch {
            _searchState.value = UiState.Loading
            val result = repository.searchAnime(query, page)
            _searchState.value = if (result.isSuccess) {
                val animes = result.getOrNull() ?: emptyList()
                if (animes.isEmpty()) {
                    UiState.Empty
                } else {
                    UiState.Success(animes)
                }
            } else {
                UiState.Error(result.exceptionOrNull()?.message ?: "Search failed")
            }
        }
    }

    // Load trending anime (PAGINATION SUPPORT)
    fun loadTrendingAnime(page: Int = 1) {
        viewModelScope.launch {
            _trendingState.value = UiState.Loading
            currentListType = ListType.TRENDING
            _currentPage.value = page

            val result = repository.getTrendingAnime(page)
            _trendingState.value = if (result.isSuccess) {
                val animes = result.getOrNull() ?: emptyList()
                if (animes.isEmpty()) {
                    UiState.Empty
                } else {
                    UiState.Success(animes)
                }
            } else {
                UiState.Error(result.exceptionOrNull()?.message ?: "Failed to load trending anime")
            }
        }
    }

    // Load upcoming anime (PAGINATION SUPPORT)
    fun loadUpcomingAnime(page: Int = 1) {
        viewModelScope.launch {
            _upcomingState.value = UiState.Loading
            currentListType = ListType.UPCOMING
            _currentPage.value = page

            val result = repository.getUpcomingAnime(page)
            _upcomingState.value = if (result.isSuccess) {
                val animes = result.getOrNull() ?: emptyList()
                if (animes.isEmpty()) {
                    UiState.Empty
                } else {
                    UiState.Success(animes)
                }
            } else {
                UiState.Error(result.exceptionOrNull()?.message ?: "Failed to load upcoming anime")
            }
        }
    }

    // Load current season anime (PAGINATION SUPPORT)
    fun loadCurrentSeasonAnime(page: Int = 1) {
        viewModelScope.launch {
            _currentSeasonState.value = UiState.Loading
            currentListType = ListType.SEASON
            _currentPage.value = page

            val result = repository.getCurrentSeasonAnime(page)
            _currentSeasonState.value = if (result.isSuccess) {
                val animes = result.getOrNull() ?: emptyList()
                if (animes.isEmpty()) {
                    UiState.Empty
                } else {
                    UiState.Success(animes)
                }
            } else {
                UiState.Error(result.exceptionOrNull()?.message ?: "Failed to load season anime")
            }
        }
    }

    // Toggle favorite status
    fun toggleFavorite(anime: Anime) {
        viewModelScope.launch {
            try {
                if (repository.isFavorite(anime.malId)) {
                    repository.removeFavorite(anime.malId)
                    _favoriteIds.value -= anime.malId
                } else {
                    repository.addFavorite(anime)
                    _favoriteIds.value += anime.malId
                }
            } catch (e: Exception) {
                Log.e("AnimeViewModel", "Error toggling favorite", e)
            }
        }
    }

    // Load all favorites to track favorite IDs (PUBLIC for pull-to-refresh)
    fun loadFavorites() {
        viewModelScope.launch {
            repository.getAllFavorites().collect { favorites ->
                _favoriteIds.value = favorites.map { it.malId }.toSet()
            }
        }
    }

    // Load next page (PAGINATION)
    fun loadNextPage() {
        val nextPage = _currentPage.value + 1
        when (currentListType) {
            ListType.TOP -> loadTopAnime(nextPage)
            ListType.TRENDING -> loadTrendingAnime(nextPage)
            ListType.UPCOMING -> loadUpcomingAnime(nextPage)
            ListType.SEASON -> loadCurrentSeasonAnime(nextPage)
        }
    }
}