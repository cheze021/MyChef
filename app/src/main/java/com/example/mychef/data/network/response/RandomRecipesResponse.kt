package com.example.mychef.data.network.response

import com.example.mychef.data.network.dto.RecipeDto

data class RandomRecipesResponse(
    val recipes: List<RecipeDto>
)