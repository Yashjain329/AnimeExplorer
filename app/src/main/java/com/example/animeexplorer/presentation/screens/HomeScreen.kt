package com.example.animeexplorer.presentation.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.animeexplorer.data.models.Anime
import com.example.animeexplorer.presentation.components.*
import com.example.animeexplorer.presentation.viewmodel.AnimeViewModel
import com.example.animeexplorer.presentation.viewmodel.UiState
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@Composable
fun HomeScreen(
    viewModel: AnimeViewModel = hiltViewModel(),
    onAnimeClick: (Int) -> Unit
) {
    var selectedTabIndex by remember { mutableStateOf(0) }
    val tabs = listOf("Top Rated", "Trending", "Upcoming", "Season")

    val topAnimeState by viewModel.topAnimeState.collectAsState()
    val trendingState by viewModel.trendingState.collectAsState()
    val upcomingState by viewModel.upcomingState.collectAsState()
    val seasonState by viewModel.currentSeasonState.collectAsState()
    val favoriteIds by viewModel.favoriteIds.collectAsState()

    Column(modifier = Modifier.fillMaxSize()) {
        TabRow(
            selectedTabIndex = selectedTabIndex,
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.primary
        ) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTabIndex == index,
                    onClick = { selectedTabIndex = index },
                    text = { Text(text = title, style = MaterialTheme.typography.labelMedium) }
                )
            }
        }

        // PULL-TO-REFRESH
        val isRefreshing by remember {
            derivedStateOf {
                listOf(topAnimeState, trendingState, upcomingState, seasonState)
                    .any { it is UiState.Loading }
            }
        }

        SwipeRefresh(
            state = rememberSwipeRefreshState(isRefreshing),
            onRefresh = { viewModel.loadHomeData() },
            modifier = Modifier.fillMaxSize()
        ) {
            when (selectedTabIndex) {
                0 -> AnimeListScreen(
                    state = topAnimeState,
                    favoriteIds = favoriteIds,
                    onAnimeClick = onAnimeClick,
                    onFavoriteClick = { anime -> viewModel.toggleFavorite(anime) },
                    onLoadMore = { viewModel.loadNextPage() }
                )
                1 -> AnimeListScreen(
                    state = trendingState,
                    favoriteIds = favoriteIds,
                    onAnimeClick = onAnimeClick,
                    onFavoriteClick = { anime -> viewModel.toggleFavorite(anime) },
                    onLoadMore = { viewModel.loadNextPage() }
                )
                2 -> AnimeListScreen(
                    state = upcomingState,
                    favoriteIds = favoriteIds,
                    onAnimeClick = onAnimeClick,
                    onFavoriteClick = { anime -> viewModel.toggleFavorite(anime) },
                    onLoadMore = { viewModel.loadNextPage() }
                )
                3 -> AnimeListScreen(
                    state = seasonState,
                    favoriteIds = favoriteIds,
                    onAnimeClick = onAnimeClick,
                    onFavoriteClick = { anime -> viewModel.toggleFavorite(anime) },
                    onLoadMore = { viewModel.loadNextPage() }
                )
            }
        }
    }
}

@Composable
private fun AnimeListScreen(
    state: UiState<List<Anime>>,
    favoriteIds: Set<Int>,
    onAnimeClick: (Int) -> Unit,
    onFavoriteClick: (Anime) -> Unit,
    onLoadMore: () -> Unit
) {
    when (state) {
        is UiState.Loading -> LoadingIndicator()
        is UiState.Empty -> EmptyScreen("No anime found")
        is UiState.Success -> Column {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier
                    .weight(1f)  // ✅ Takes available space
                    .fillMaxWidth(),
                contentPadding = PaddingValues(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(state.data) { anime ->
                    AnimeCard(
                        anime = anime,
                        isFavorite = favoriteIds.contains(anime.malId),
                        onClick = { onAnimeClick(anime.malId) },
                        onFavoriteClick = { onFavoriteClick(anime) }
                    )
                }
            }

            // ✅ PAGINATION BUTTON
            Button(
                onClick = onLoadMore,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                enabled = state.data.isNotEmpty()
            ) {
                Text("Load More (${state.data.size} anime loaded)")
            }
        }
        is UiState.Error -> ErrorScreen(
            message = state.message,
            onRetry = onLoadMore
        )
    }
}