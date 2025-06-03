package com.example.mychef.data.network.response

import com.example.mychef.data.network.dto.RecipeDto

data class SearchRecipesResponse(
    val results: List<RecipeDto>
)