package com.example.animeexplorer.presentation.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.animeexplorer.presentation.components.*
import com.example.animeexplorer.presentation.viewmodel.AnimeViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@Composable
fun FavoritesScreen(
    viewModel: AnimeViewModel = hiltViewModel(),
    onAnimeClick: (Int) -> Unit
) {
    val favoriteIds by viewModel.favoriteIds.collectAsState()

    SwipeRefresh(
        state = rememberSwipeRefreshState(false),
        onRefresh = { viewModel.loadFavorites() },
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(
                text = "My Favorites",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onBackground
            )

            Spacer(modifier = Modifier.height(16.dp))

            if (favoriteIds.isEmpty()) {
                Text(
                    text = "Your favorite anime will appear here.\nAdd anime to favorites from their detail pages!",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            } else {
                Text(
                    text = "${favoriteIds.size} favorites",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}