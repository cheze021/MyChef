package com.example.mychef.data.network.dto

import com.example.mychef.model.nutrition.NutritionInfo

data class NutritionInfoDto(
    val calories: String,
    val carbs: String,
    val fat: String,
    val protein: String,
    val bad: List<BadValuesDto>,
    val good: List<GoodValuesDto>
) {
    fun toDomain() = NutritionInfo(
        calories = calories,
        carbs = carbs,
        fat = fat,
        protein = protein,
        bad = bad.map { it.toDomain() },
        good = good.map { it.toDomain() }
    )
}

