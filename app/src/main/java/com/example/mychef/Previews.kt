package com.example.mychef

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.mychef.model.category.CategoryProvider
import com.example.mychef.model.nutrition.BadValues
import com.example.mychef.model.nutrition.GoodValues
import com.example.mychef.model.nutrition.NutritionInfo
import com.example.mychef.ui.favorites.CategoryFilterBar
import com.example.mychef.ui.favorites.CategoryFilterChips
import com.example.mychef.ui.home.HomeScreen
import com.example.mychef.ui.recipe_detail.NutritionGrid
import com.example.mychef.ui.recipe_detail.RecipeDetailScreen
import com.example.mychef.ui.recipe_detail.RecipeNutritionScreen

@Composable
@Preview(showBackground = true)
fun HomePreview() {
    val navController = rememberNavController()
    HomeScreen(navController)
}

@Composable
@Preview(showBackground = true)
fun RecipeDetailPreview() {
    val recipeId = 1826
    val navController = rememberNavController()
    RecipeDetailScreen(
        recipeId = recipeId,
        navController = navController
    )
}

@Composable
@Preview(showBackground = true)
fun RecipeNutritionScreenPreview() {
    val fakeNutritionInfo = NutritionInfo(
        calories = "100 kcal",
        carbs = "20 g",
        fat = "5 g",
        protein = "10 g",
        bad = listOf(
            BadValues("Sodium", "100 mg"),
            BadValues("Sodium", "100 mg"),
            BadValues("Sodium", "100 mg"),
            BadValues("Sodium", "100 mg"),
            BadValues("Sodium", "100 mg"),
            BadValues("Sodium", "100 mg"),
            BadValues("Potassium", "200 mg")
        ),
        good = listOf(
            GoodValues("Vitamin A", "100 IU"),
            GoodValues("Vitamin A", "100 IU"),
            GoodValues("Vitamin A", "100 IU"),
            GoodValues("Vitamin A", "100 IU"),
            GoodValues("Vitamin A", "100 IU"),
            GoodValues("Vitamin A", "100 IU"),
            GoodValues("Vitamin A", "100 IU"),
            GoodValues("Vitamin A", "100 IU"),
            GoodValues("Vitamin A", "100 IU"),
            GoodValues("Vitamin A", "100 IU"),
            GoodValues("Vitamin A", "100 IU"),
            GoodValues("Vitamin C", "200 mg")
        )
    )

    RecipeNutritionScreen(
        fakeNutritionInfo
    )
}

@Preview(showBackground = true)
@Composable
fun FilterChipPreview() {
    CategoryFilterBar()
}