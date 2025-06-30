package com.example.mychef.data.network.dto

import com.example.mychef.model.Recipe
import com.google.gson.annotations.SerializedName

data class RecipeDetailDto(
    val id: Int,
    val title: String,
    val image: String,
    val readyInMinutes: Int,
    val servings: Int,
    val preparationMinutes: Int,
    val cookingMinutes: Int,
    @SerializedName("extendedIngredients")
    val ingredients: List<IngredientDto>,
    @SerializedName("dishTypes")
    val category: List<String>
) {
    fun toDomain() = Recipe(
        id = id,
        title = title,
        imageUrl = image,
        readyInMinutes = readyInMinutes,
        servings = servings,
        preparationMinutes = preparationMinutes,
        cookingMinutes = cookingMinutes,
        ingredients = ingredients.map { it.toDomain() },
        category = category
    )
}