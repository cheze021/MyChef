package com.example.mychef.domain

import com.example.mychef.model.Recipe
import kotlinx.coroutines.flow.StateFlow

interface FavoritesRepository {
    fun addToFavorites(recipe: Recipe)
    fun removeFromFavorites(recipeId: Int)
    fun getFavorites(): StateFlow<List<Recipe>>
    fun isFavorite(recipeId: Int): Boolean
}