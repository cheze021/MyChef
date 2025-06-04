package com.example.mychef.presentation.recipe

import com.example.mychef.model.Recipe

data class RecipeUiState(
    val featuredRecipes: List<Recipe> = emptyList(),
    val selectedRecipe: Recipe? = null
)