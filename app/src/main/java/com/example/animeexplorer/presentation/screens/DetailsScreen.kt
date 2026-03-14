package com.example.animeexplorer.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.animeexplorer.presentation.components.*
import com.example.animeexplorer.presentation.viewmodel.DetailsViewModel
import com.example.animeexplorer.presentation.viewmodel.UiState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.ui.graphics.Brush



//Detailed anime information screen
@Composable
fun DetailsScreen(
    animeId: Int,
    viewModel: DetailsViewModel = hiltViewModel(),
    onBackClick: () -> Unit
) {
    val animeState by viewModel.animeDetails.collectAsState()
    val charactersState by viewModel.characters.collectAsState()
    val isFavorite by viewModel.isFavorite.collectAsState()
    val isInWatchlist by viewModel.isInWatchlist.collectAsState()

    LaunchedEffect(animeId) {
        viewModel.loadAnimeDetails(animeId)
    }

    when (animeState) {
        is UiState.Loading -> {
            LoadingIndicator()
        }
        is UiState.Empty -> {
            EmptyScreen("Anime not found")
        }
        is UiState.Success -> {
            val anime = (animeState as UiState.Success).data
            DetailsContent(
                anime = anime,
                characters = charactersState,
                isFavorite = isFavorite,
                isInWatchlist = isInWatchlist,
                onBackClick = onBackClick,
                onFavoriteClick = { viewModel.toggleFavorite(anime) },
                onWatchlistClick = { viewModel.toggleWatchlist(anime) }
            )
        }
        is UiState.Error -> {
            ErrorScreen(
                message = (animeState as UiState.Error).message,
                onRetry = { viewModel.loadAnimeDetails(animeId) }
            )
        }
    }
}

@Composable
private fun DetailsContent(
    anime: com.example.animeexplorer.data.models.Anime,
    characters: UiState<List<com.example.animeexplorer.data.models.Character>>,
    isFavorite: Boolean,
    isInWatchlist: Boolean,
    onBackClick: () -> Unit,
    onFavoriteClick: () -> Unit,
    onWatchlistClick: () -> Unit
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Hero Image Section
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
        ) {
            val imageUrl = anime.images.jpg?.largeImageUrl
                ?: anime.images.webp?.largeImageUrl
                ?: ""

            AsyncImage(
                model = imageUrl,
                contentDescription = anime.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )

            // Gradient overlay
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                MaterialTheme.colorScheme.background
                            ),
                            startY = 200f
                        )
                    )
            )

            // Top Bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = onBackClick,
                    modifier = Modifier
                        .background(
                            color = Color.Black.copy(alpha = 0.7f),
                            shape = RoundedCornerShape(8.dp)
                        )
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.White
                    )
                }

                Row {
                    IconButton(
                        onClick = onFavoriteClick,
                        modifier = Modifier
                            .background(
                                color = Color.Black.copy(alpha = 0.7f),
                                shape = RoundedCornerShape(8.dp)
                            )
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Favorite,
                            contentDescription = "Favorite",
                            tint = if (isFavorite) {
                                MaterialTheme.colorScheme.primary
                            } else {
                                Color.White
                            }
                        )
                    }
                }
            }
        }

        // Content Section
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Title
            Text(
                text = anime.title,
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onBackground
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Rating and Info Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                InfoChip(
                    label = anime.score?.let { "★ $it" } ?: "N/A",
                    backgroundColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)
                )

                if (anime.episodes != null) {
                    InfoChip(
                        label = "${anime.episodes} Episodes",
                        backgroundColor = MaterialTheme.colorScheme.secondary.copy(alpha = 0.2f)
                    )
                }

                if (anime.status != null) {
                    InfoChip(
                        label = anime.status,
                        backgroundColor = MaterialTheme.colorScheme.tertiary.copy(alpha = 0.2f)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Action Buttons
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = onFavoriteClick,
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (isFavorite) {
                            MaterialTheme.colorScheme.primary
                        } else {
                            MaterialTheme.colorScheme.surface
                        }
                    )
                ) {
                    Text(
                        text = if (isFavorite) "❤ Favorited" else "♡ Favorite",
                        color = if (isFavorite) {
                            MaterialTheme.colorScheme.onPrimary
                        } else {
                            MaterialTheme.colorScheme.onSurface
                        }
                    )
                }

                Button(
                    onClick = onWatchlistClick,
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (isInWatchlist) {
                            MaterialTheme.colorScheme.secondary
                        } else {
                            MaterialTheme.colorScheme.surface
                        }
                    )
                ) {
                    Text(
                        text = if (isInWatchlist) "✓ Watchlist" else "+ Watchlist",
                        color = if (isInWatchlist) {
                            MaterialTheme.colorScheme.onSecondary
                        } else {
                            MaterialTheme.colorScheme.onSurface
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Synopsis Section
            if (anime.synopsis != null) {
                Text(
                    text = "Synopsis",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = anime.synopsis,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(24.dp))
            }

            // Genres
            if (anime.genres.isNotEmpty()) {
                Text(
                    text = "Genres",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    anime.genres.forEach { genre ->
                        Surface(
                            color = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f),
                            shape = RoundedCornerShape(20.dp)
                        ) {
                            Text(
                                text = genre.name,
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.padding(12.dp, 6.dp)
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(24.dp))
            }

            // Characters Section
            if (characters is UiState.Success) {
                val charList = characters.data
                if (charList.isNotEmpty()) {
                    Text(
                        text = "Characters",
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Spacer(modifier = Modifier.height(12.dp))

                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        charList.take(5).forEach { character ->
                            CharacterItem(character)
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun CharacterItem(
    character: com.example.animeexplorer.data.models.Character
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        val imageUrl = character.character?.images?.jpg?.imageUrl
            ?: character.character?.images?.webp?.imageUrl
            ?: ""

        if (imageUrl.isNotEmpty()) {
            AsyncImage(
                model = imageUrl,
                contentDescription = character.character?.name ?: "",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(40.dp)
                    .background(
                        color = MaterialTheme.colorScheme.surfaceVariant,
                        shape = RoundedCornerShape(4.dp)
                    )
            )
            Spacer(modifier = Modifier.width(12.dp))
        }

        Column {
            Text(
                text = character.character?.name ?: "Unknown",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onBackground
            )
            Text(
                text = character.role ?: "Unknown",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}