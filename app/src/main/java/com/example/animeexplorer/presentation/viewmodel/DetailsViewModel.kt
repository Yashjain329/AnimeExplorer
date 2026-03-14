package com.example.animeexplorer.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.animeexplorer.data.models.Anime
import com.example.animeexplorer.data.models.Character
import com.example.animeexplorer.data.repository.AnimeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


 //ViewModel for anime details screen
@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val repository: AnimeRepository
) : ViewModel() {

    private val _animeDetails = MutableStateFlow<UiState<Anime>>(UiState.Loading)
    val animeDetails: StateFlow<UiState<Anime>> = _animeDetails.asStateFlow()

    private val _characters = MutableStateFlow<UiState<List<Character>>>(UiState.Loading)
    val characters: StateFlow<UiState<List<Character>>> = _characters.asStateFlow()

    private val _isFavorite = MutableStateFlow(false)
    val isFavorite: StateFlow<Boolean> = _isFavorite.asStateFlow()

    private val _isInWatchlist = MutableStateFlow(false)
    val isInWatchlist: StateFlow<Boolean> = _isInWatchlist.asStateFlow()


    //Load anime details
    fun loadAnimeDetails(id: Int) {
        viewModelScope.launch {
            _animeDetails.value = UiState.Loading

            val result = repository.getAnimeDetails(id)
            _animeDetails.value = if (result.isSuccess) {
                val anime = result.getOrNull()
                if (anime != null) {
                    checkFavoriteStatus(id)
                    checkWatchlistStatus(id)
                    loadCharacters(id)
                    UiState.Success(anime)
                } else {
                    UiState.Empty
                }
            } else {
                UiState.Error(result.exceptionOrNull()?.message ?: "Failed to load anime details")
            }
        }
    }


    //Load characters for anime
    private fun loadCharacters(id: Int) {
        viewModelScope.launch {
            val result = repository.getAnimeCharacters(id)
            _characters.value = if (result.isSuccess) {
                val chars = result.getOrNull() ?: emptyList()
                if (chars.isEmpty()) UiState.Empty else UiState.Success(chars)
            } else {
                UiState.Error(result.exceptionOrNull()?.message ?: "Failed to load characters")
            }
        }
    }


    //Check if anime is in favorites
    private suspend fun checkFavoriteStatus(id: Int) {
        _isFavorite.value = repository.isFavorite(id)
    }


    //Check if anime is in watchlist
    private suspend fun checkWatchlistStatus(id: Int) {
        _isInWatchlist.value = repository.isInWatchlist(id)
    }


    //Toggle favorite
    fun toggleFavorite(anime: Anime) {
        viewModelScope.launch {
            if (_isFavorite.value) {
                repository.removeFavorite(anime.malId)
                _isFavorite.value = false
            } else {
                repository.addFavorite(anime)
                _isFavorite.value = true
            }
        }
    }


    //Toggle watchlist
    fun toggleWatchlist(anime: Anime) {
        viewModelScope.launch {
            if (_isInWatchlist.value) {
                repository.removeFromWatchlist(anime.malId)
                _isInWatchlist.value = false
            } else {
                repository.addToWatchlist(anime)
                _isInWatchlist.value = true
            }
        }
    }
}