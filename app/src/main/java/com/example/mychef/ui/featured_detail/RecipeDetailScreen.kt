package com.example.mychef.ui.featured_detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.mychef.presentation.recipe.RecipeViewModel

@Composable
fun RecipeDetailScreen(recipeId: Int, recipeViewModel: RecipeViewModel) {
    val state by recipeViewModel.uiState.collectAsState()

    LaunchedEffect(recipeId) {
        recipeViewModel.loadRecipeDetail(recipeId)
    }

    val recipe = state.selectedRecipe

    if (recipe != null) {
        Column(Modifier.padding(16.dp)) {
            Text(text = recipe.title, style = MaterialTheme.typography.titleLarge)
            Spacer(Modifier.height(8.dp))
            AsyncImage(model = recipe.imageUrl, contentDescription = null)
            Spacer(Modifier.height(8.dp))
            Text(text = "Ingredients:", fontWeight = FontWeight.Bold)
            recipe.ingredients.forEach { Text("• $it") }
            Spacer(Modifier.height(8.dp))
        }
    } else {
        CircularProgressIndicator()
    }
}