package com.example.mychef.data.network.dto

import com.example.mychef.model.Recipe

data class RecipeDto(
    val id: Int,
    val title: String,
    val image: String?,
    val readyInMinutes: Int?,
    val servings: Int?,
    val sourceUrl: String?
) {
    fun toDomain() = Recipe(
        id = id,
        title = title,
        imageUrl = image ?: "",
        readyInMinutes = readyInMinutes ?: 0,
        servings = servings ?: 0,
        sourceUrl = sourceUrl ?: ""
    )
}