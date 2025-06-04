package com.example.mychef.ui.category_recipe

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.mychef.presentation.recipe.RecipeViewModel

@Composable
fun RecipeByCategoryScreen(
    categoryName: String,
    recipeViewModel: RecipeViewModel = hiltViewModel()
) {
    val state by recipeViewModel.uiState.collectAsState()

    LaunchedEffect(categoryName) {
        recipeViewModel.loadRecipeByCategory(categoryName)
    }

    when {
        state.isLoading -> {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        state.error != null -> {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Error: ${state.error}")
            }
        }

        state.recipesByCategory.isNotEmpty() -> {
            LazyColumn {
                itemsIndexed(state.recipesByCategory) { _, recipe ->
                    Column(Modifier.padding(16.dp)) {
                        Text(text = recipe.title, style = MaterialTheme.typography.titleLarge)
                        Spacer(Modifier.height(8.dp))
                        AsyncImage(model = recipe.imageUrl, contentDescription = null)
                        Spacer(Modifier.height(8.dp))
                        recipe.ingredients.forEach { Text("• $it") }
                        Spacer(Modifier.height(8.dp))
                    }
                }
            }
        }

        else -> {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("No recipes found in this category.")
            }
        }
    }
}