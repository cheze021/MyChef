package com.example.mychef.model

data class Recipe(
    val id: Int,
    val title: String,
    val imageUrl: String,
    val readyInMinutes: Int? = null,
    val servings: Int? = null,
    val preparationMinutes: Int? = null,
    val cookingMinutes: Int? = null,
    val ingredients: List<Ingredient> = emptyList(),
    val category: List<String> = emptyList(),
    val calories: Int?? = null,
    val protein: String? = null,
    val fat: String? = null,
    val carbs: String? = null,
    val calcium: String? = null,
    val saturatedFat: String? = null,
    val fiber: String? = null,
    val potassium: String? = null,
    val sodium: String? = null,
    val sugar: String? = null,
)