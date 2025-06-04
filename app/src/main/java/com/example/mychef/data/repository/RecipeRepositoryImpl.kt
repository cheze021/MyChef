package com.example.mychef.data.repository

import com.example.mychef.data.network.SpoonacularApi
import com.example.mychef.domain.RecipeRepository
import com.example.mychef.model.Ingredient
import com.example.mychef.model.Recipe
import javax.inject.Inject

class RecipeRepositoryImpl @Inject constructor(
    private val api: SpoonacularApi
) : RecipeRepository {
    private val apiKey = "API_KEY"

    override suspend fun fetchRecipes(): List<Recipe> {

        // Aquí iría el llamado a la API
        return listOf(
            Recipe(
                id = 1,
                title = "Pasta con tomate",
                imageUrl = "https://...",
                readyInMinutes = 20,
                servings = 2,
                ingredients = listOf(
                    Ingredient(1, "Tomato", 2.0, "units"),
                    Ingredient(2, "Pasta", 200.0, "grams")
                )
            )
        )

    }

    override suspend fun getRandomRecipes(number: Int): List<Recipe> {
        return api.getRandomRecipes(number, apiKey).recipes.map { it.toDomain() }
    }

    override suspend fun searchRecipes(query: String): List<Recipe> {
        return api.searchRecipes(query, number = 10, apiKey = apiKey).results.map { it.toDomain() }
    }

    override suspend fun getRecipesByCategory(category: String): List<Recipe> {
        return api.getRecipesByCategory(type = category, number = 10, apiKey = apiKey).results.map { it.toDomain() }
    }

    override suspend fun getRecipeDetail(id: Int): Recipe {
        return api.getRecipeDetail(id, apiKey).toDomain()
    }
}