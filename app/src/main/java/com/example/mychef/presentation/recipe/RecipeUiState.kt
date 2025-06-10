package com.example.mychef.presentation.recipe

import com.example.mychef.model.nutrition.NutritionInfo
import com.example.mychef.model.Recipe

data class RecipeUiState(
    val featuredRecipes: List<Recipe> = emptyList(),
    val selectedRecipe: Recipe? = null,
    val recipesByCategory: List<Recipe> = emptyList(),
    val recipeNutrients: NutritionInfo? = null,
    val isSuccess: Boolean = false,
    val isLoading: Boolean = false,
    val error: String? = null
)