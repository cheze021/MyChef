package com.example.mychef.domain

import com.example.mychef.model.nutrition.NutritionInfo
import com.example.mychef.model.Recipe

interface RecipeRepository {
    suspend fun fetchRecipes(): List<Recipe>
    suspend fun getRandomRecipes(number: Int): List<Recipe>
    suspend fun searchRecipes(query: String): List<Recipe>
    suspend fun getRecipesByCategory(category: String): List<Recipe>
    suspend fun getRecipeDetail(id: Int): Recipe
    suspend fun getRecipeNutrients(id: Int): NutritionInfo
}