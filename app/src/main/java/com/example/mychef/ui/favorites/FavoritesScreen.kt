package com.example.mychef.ui.favorites

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.mychef.R
import com.example.mychef.model.Recipe
import com.example.mychef.presentation.recipe.RecipeViewModel
import com.example.mychef.ui.theme.quickSandFamily
import com.example.mychef.utils.StyledDarkerButton

@Composable
fun FavoritesScreen(
    recipeViewModel: RecipeViewModel = hiltViewModel(),
    navController: NavController
) {
    val state by recipeViewModel.uiState.collectAsState()
    val favorites by state.favorites.collectAsState()

    LaunchedEffect(Unit) {
        recipeViewModel.getFavorites()
    }

    when {
        state.isLoading -> {
            Box(Modifier
                .fillMaxSize()
                .background(Color(0xFFFFF7F7)),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        state.error != null -> {
            Box(
                Modifier
                    .fillMaxSize()
                    .background(Color(0xFFFFF7F7)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Error: ${state.error}",
                    fontFamily = quickSandFamily,
                )
            }
        }

        state.isSuccess -> {
            if (favorites.isEmpty()) {
                FavoritesEmptyCardList()
            } else {
                FavoritesCardList(favorites)
            }
        }
    }
}

@Composable
private fun FavoritesEmptyCardList() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFF7F7)),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.padding(24.dp)
        ) {
            AsyncImage(
                model = R.drawable.empty_recipes,
                contentDescription = null,
                modifier = Modifier
                    .height(200.dp)
                    .width(380.dp)
                    .clip(RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Crop
            )

            Text(
                text = "No favorites yet",
                fontFamily = quickSandFamily,
                style = MaterialTheme.typography.titleMedium,
                color = Color(0xFF1E1E1E)
            )

            Text(
                text = "Save your favorites recipes to easily access them later.",
                fontFamily = quickSandFamily,
                style = MaterialTheme.typography.bodyMedium,
                color = Color(0xFF444444),
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 24.dp)
            )

            StyledDarkerButton({  }, "Explore Recipes")
        }
    }
}

@Composable
private fun FavoritesCardList(favorites: List<Recipe>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFF7F7)),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        itemsIndexed(favorites) { _, recipe ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.Transparent
                ),
                shape = RoundedCornerShape(12.dp),
                elevation = CardDefaults.cardElevation(0.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.White)
                        .padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    AsyncImage(
                        model = recipe.imageUrl,
                        contentDescription = null,
                        modifier = Modifier
                            .size(80.dp)
                            .clip(RoundedCornerShape(8.dp)),
                        contentScale = ContentScale.Crop
                    )

                    Spacer(modifier = Modifier.width(12.dp))

                    Text(
                        text = recipe.title,
                        fontFamily = quickSandFamily,
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color(0xFF333333)
                    )
                }
            }
        }
    }
}