package com.example.mychef.data.network.dto

import com.example.mychef.model.Recipe

data class RecipeByNutrientDto(
    val id: Int,
    val title: String,
    val image: String,
    val imageType: String,
    val calories: Int,
    val protein: String,
    val fat: String,
    val carbs: String?,
    val calcium: String?,
    val saturatedFat: String?,
    val fiber: String?,
    val potassium: String?,
    val sodium: String?,
    val sugar: String?,
    val number : Int
) {
    fun toDomain() = Recipe(
        id = id,
        title = title,
        imageUrl = image,
        calories = calories,
        protein = protein,
        fat = fat,
        carbs = carbs,
        calcium = calcium,
        saturatedFat = saturatedFat,
        fiber = fiber,
        potassium = potassium,
        sodium = sodium,
        sugar = sugar
    )
}
