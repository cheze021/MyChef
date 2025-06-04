package com.example.mychef.model

data class Recipe(
    val id: Int,
    val title: String,
    val imageUrl: String,
    val readyInMinutes: Int,
    val servings: Int,
    val ingredients: List<Ingredient> = emptyList()
)