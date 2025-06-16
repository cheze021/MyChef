package com.example.mychef.data.repository

import android.util.Log
import com.example.mychef.domain.FavoritesRepository
import com.example.mychef.model.Recipe
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class FavoritesRepositoryImpl @Inject constructor() : FavoritesRepository {
    private val favorites = MutableStateFlow<List<Recipe>>(emptyList())

    override fun addToFavorites(recipe: Recipe) {
        if (!favorites.value.any { it.id == recipe.id }) {
            favorites.value += recipe
        }
        Log.d("FavoritesList", favorites.value.toString())
    }

    override fun removeFromFavorites(recipeId: Int) {
        favorites.value = favorites.value.filterNot { it.id == recipeId }
        Log.d("FavoritesList", favorites.value.toString())
    }

    override fun getFavorites(): StateFlow<List<Recipe>> = favorites

    override fun isFavorite(recipeId: Int): Boolean {
        return favorites.value.any { it.id == recipeId }
    }
}