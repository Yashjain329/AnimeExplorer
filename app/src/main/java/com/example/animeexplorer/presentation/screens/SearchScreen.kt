package com.example.animeexplorer.presentation.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.animeexplorer.presentation.components.*
import com.example.animeexplorer.presentation.viewmodel.AnimeViewModel
import com.example.animeexplorer.presentation.viewmodel.UiState
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


//Search screen with real-time search
@Composable
fun SearchScreen(
    viewModel: AnimeViewModel = hiltViewModel(),
    onAnimeClick: (Int) -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }
    val searchState by viewModel.searchState.collectAsState()
    val favoriteIds by viewModel.favoriteIds.collectAsState()

    // Debounced search
    var searchJob by remember { mutableStateOf<Job?>(null) }
    val coroutineScope = rememberCoroutineScope()

    Column(modifier = Modifier.fillMaxSize()) {
        // Search Bar
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { newQuery ->
                searchQuery = newQuery

                searchJob?.cancel()
                searchJob = coroutineScope.launch {
                    delay(500) // Debounce delay
                    if (newQuery.isNotBlank()) {
                        viewModel.searchAnime(newQuery)
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            placeholder = { Text("Search anime...") },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search"
                )
            },
            trailingIcon = {
                if (searchQuery.isNotEmpty()) {
                    IconButton(
                        onClick = { searchQuery = "" }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Clear"
                        )
                    }
                }
            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions(
                onSearch = {
                    if (searchQuery.isNotBlank()) {
                        viewModel.searchAnime(searchQuery)
                    }
                }
            ),
            singleLine = true,
            shape = RoundedCornerShape(12.dp)
        )

        // Search Results
        when (searchState) {
            is UiState.Loading -> {
                LoadingIndicator()
            }
            is UiState.Empty -> {
                if (searchQuery.isBlank()) {
                    EmptyScreen("Search for your favorite anime")
                } else {
                    EmptyScreen("No results found for \"$searchQuery\"")
                }
            }
            is UiState.Success -> {
                val animeList = (searchState as UiState.Success).data
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(animeList) { anime ->
                        AnimeCard(
                            anime = anime,
                            isFavorite = favoriteIds.contains(anime.malId),
                            onClick = { onAnimeClick(anime.malId) },
                            onFavoriteClick = { viewModel.toggleFavorite(anime) }
                        )
                    }
                }
            }
            is UiState.Error -> {
                ErrorScreen(
                    message = (searchState as UiState.Error).message,
                    onRetry = {
                        if (searchQuery.isNotBlank()) {
                            viewModel.searchAnime(searchQuery)
                        }
                    }
                )
            }
        }
    }
}