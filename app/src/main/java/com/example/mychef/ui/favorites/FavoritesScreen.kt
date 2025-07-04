package com.example.mychef.ui.favorites

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.mychef.R
import com.example.mychef.model.Recipe
import com.example.mychef.model.category.Category
import com.example.mychef.model.category.CategoryProvider
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
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(0xFFFFF7F7))
                ) {
                    var selectedCategories by remember { mutableStateOf<List<Int>>(emptyList()) }

                    CategoryFilterChips(
                        categories = CategoryProvider.categories,
                        selectedCategories = selectedCategories,
                        onCategorySelected = { categoryId ->
                            selectedCategories = when (categoryId) {
                                null -> emptyList()
                                else -> {
                                    if (selectedCategories.contains(categoryId)) {
                                        selectedCategories - categoryId
                                    } else {
                                        selectedCategories + categoryId
                                    }
                                }
                            }
                        }
                    )

                    val groupedFavorites = if (selectedCategories.isEmpty()) {
                        favorites.groupBy { recipe ->
                            CategoryProvider.categories.firstOrNull { cat ->
                                recipe.category.contains(cat.name)
                            }?.name ?: "Other"
                        }
                    } else {
                        favorites
                            .filter { recipe ->
                                CategoryProvider.categories.any { cat ->
                                    cat.id in selectedCategories && recipe.category.contains(cat.name)
                                }
                            }
                            .groupBy { recipe ->
                                CategoryProvider.categories.firstOrNull { cat ->
                                    cat.id in selectedCategories && recipe.category.contains(cat.name)
                                }?.name ?: "Other"
                            }
                    }

                    if (groupedFavorites.isEmpty()) {
                        FavoritesEmptyCardList()
                    } else {
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(Color(0xFFFFF7F7))
                        ) {
                            groupedFavorites.forEach { (categoryName, recipesInCategory) ->
                                item {
                                    Text(
                                        text = categoryName.replaceFirstChar { it.uppercaseChar() },
                                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                                        color = Color(0xFF3D2B2B),
                                        fontFamily = quickSandFamily,
                                        modifier = Modifier
                                            .padding(start = 16.dp, top = 12.dp, bottom = 8.dp)
                                    )
                                }

                                item {
                                    LazyVerticalGrid(
                                        columns = GridCells.Fixed(2),
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .heightIn(max = 1000.dp), // Estimación de alto para no romper LazyColumn
                                        contentPadding = PaddingValues(horizontal = 12.dp),
                                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                                        verticalArrangement = Arrangement.spacedBy(12.dp),
                                        userScrollEnabled = false // Desactiva scroll interno
                                    ) {
                                        itemsIndexed(recipesInCategory) { _, recipe ->
                                            FavoriteRecipeCard(recipe)
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
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
fun FavoriteRecipeCard(recipe:Recipe) {
    Column(
        modifier = Modifier
            .width(180.dp)
            .padding(8.dp)
    ) {
        AsyncImage(
            model = recipe.imageUrl,
            contentDescription = recipe.title,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .clip(RoundedCornerShape(16.dp))
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = recipe.title,
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold),
            fontFamily = quickSandFamily,
            color = Color(0xFF3D2B2B),
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = "${recipe.readyInMinutes} min · ${recipe.servings} servings",
            style = MaterialTheme.typography.bodyMedium,
            fontFamily = quickSandFamily,
            color = Color(0xFFE07061)
        )
    }
}

@Composable
fun CategoryFilterChips(
    categories: List<Category>,
    selectedCategories: List<Int>,
    onCategorySelected: (Int?) -> Unit
) {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // Chip "All"
        item {
            val selected = selectedCategories.isEmpty()
            FilterChip(
                selected = selected,
                onClick = { onCategorySelected(null) },
                label = {
                    Text(
                        text = "All",
                        fontFamily = quickSandFamily,
                        fontWeight = FontWeight.Medium,
                        color = if (selected) Color.White else Color(0xFFE07061)
                    )
                },
                colors = FilterChipDefaults.filterChipColors(
                    containerColor = Color(0xFFFFF7F7),
                    selectedContainerColor = Color(0xFFE07061),
                    labelColor = Color(0xFFE07061),
                    selectedLabelColor = Color.White
                ),
                shape = RoundedCornerShape(12.dp),
                border = BorderStroke(1.dp, Color(0xFFE07061))
            )
        }

        // Categroy chips
        itemsIndexed(categories) { _, category ->
            val selected = selectedCategories.contains(category.id)

            FilterChip(
                selected = selected,
                onClick = { onCategorySelected(category.id) },
                label = {
                    Text(
                        text = category.name.replaceFirstChar { it.uppercaseChar() },
                        fontFamily = quickSandFamily,
                        fontWeight = FontWeight.Medium,
                        color = if (selected) Color.White else Color(0xFFE07061)
                    )
                },
                colors = FilterChipDefaults.filterChipColors(
                    containerColor = Color(0xFFFFF7F7),
                    selectedContainerColor = Color(0xFFE07061),
                    labelColor = Color(0xFFE07061),
                    selectedLabelColor = Color.White
                ),
                shape = RoundedCornerShape(12.dp),
                border = BorderStroke(1.dp, Color(0xFFE07061))
            )
        }
    }
}