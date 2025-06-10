package com.example.mychef.model.nutrition

data class NutritionInfo(
    val calories: String,
    val carbs: String,
    val fat: String,
    val protein: String,
    val bad: List<BadValues>,
    val good: List<GoodValues>
)