package com.example.mychef.data.network.response

import com.example.mychef.data.network.dto.RecipeByNutrientDto

data class RecipeByNutrientResponse (
    val result: List<RecipeByNutrientDto>
)