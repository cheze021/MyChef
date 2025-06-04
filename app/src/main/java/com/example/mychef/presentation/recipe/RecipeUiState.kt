package com.example.mychef.presentation.recipe

import com.example.mychef.model.Recipe

data class RecipeUiState(
    val featuredRecipes: List<Recipe> = emptyList(),
    val selectedRecipe: Recipe? = null,
    val recipesByCategory: List<Recipe> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)