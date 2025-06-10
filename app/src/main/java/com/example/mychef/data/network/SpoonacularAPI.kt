package com.example.mychef.data.network

import com.example.mychef.data.network.dto.NutritionInfoDto
import com.example.mychef.data.network.dto.RecipeDetailDto
import com.example.mychef.data.network.response.RandomRecipesResponse
import com.example.mychef.data.network.response.SearchRecipesResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface SpoonacularApi {
    @GET("recipes/random")
    suspend fun getRandomRecipes(
        @Query("number") number: Int,
        @Query("apiKey") apiKey: String
    ): RandomRecipesResponse

    @GET("recipes/complexSearch")
    suspend fun searchRecipes(
        @Query("query") query: String,
        @Query("number") number: Int,
        @Query("apiKey") apiKey: String
    ): SearchRecipesResponse

    @GET("recipes/complexSearch")
    suspend fun getRecipesByCategory(
        @Query("type") type: String,
        @Query("number") number: Int,
        @Query("apiKey") apiKey: String
    ): SearchRecipesResponse

    @GET("recipes/{id}/information")
    suspend fun getRecipeDetail(
        @Path("id") id: Int,
        @Query("apiKey") apiKey: String
    ): RecipeDetailDto

    @GET("recipes/{id}/nutritionWidget.json")
    suspend fun getRecipeNutrients(
        @Path("id") id: Int,
        @Query("apiKey") apiKey: String
    ): NutritionInfoDto
}