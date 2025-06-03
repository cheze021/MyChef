package com.example.mychef.data.network.dto

import com.example.mychef.model.Ingredient

data class IngredientDto(
    val id: Int,
    val name: String,
    val amount: Double,
    val unit: String
) {
    fun toDomain() = Ingredient(
        id = id,
        name = name,
        amount = amount,
        unit = unit
    )
}