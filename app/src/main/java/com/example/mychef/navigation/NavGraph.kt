package com.example.mychef.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.mychef.model.nutrition.NutritionInfo
import com.example.mychef.presentation.recipe.RecipeViewModel
import com.example.mychef.ui.cart.CartScreen
import com.example.mychef.ui.category_recipe.RecipeByCategoryScreen
import com.example.mychef.ui.favorites.FavoritesScreen
import com.example.mychef.ui.home.HomeScreen
import com.example.mychef.ui.recipe_detail.RecipeDetailScreen
import com.example.mychef.ui.recipe_detail.RecipeNutritionScreen
import com.example.mychef.ui.search.SearchScreen

@Composable
fun NavGraph(
    navController: NavHostController,
    padding: PaddingValues
) {
    val recipeViewModel: RecipeViewModel = hiltViewModel()

    NavHost(
        navController = navController,

        // set the start destination as home
        startDestination = "home",

        // Set the padding provided by scaffold
        modifier = Modifier.padding(paddingValues = padding),

        builder = {

            // route : Home
            composable("home") {
                HomeScreen(navController = navController)
            }

            // route : favorites
            composable("favorites") {
                FavoritesScreen(navController = navController)
            }

            // route : search
            composable("search") {
                SearchScreen()
            }

            // route : cart
            composable("cart") {
                CartScreen()
            }

            // route : recipesByCategory/{category}
            composable(
                "recipesByCategory/{category}",
                arguments = listOf(navArgument("category") { type = NavType.StringType })
            ) { backStackEntry ->
                val recipeByCat = backStackEntry.arguments?.getString("category") ?: ""
                RecipeByCategoryScreen(recipeByCat, recipeViewModel, navController)
            }

            // route : recipeDetail
            composable(
                "recipeDetail/{recipeId}",
                arguments = listOf(navArgument("recipeId") { type = NavType.IntType })
            ) { backStackEntry ->
                val recipeId = backStackEntry.arguments?.getInt("recipeId") ?: 0
                RecipeDetailScreen(recipeId, recipeViewModel, navController)
            }

            // route : recipeNutrition
            composable("recipeNutrition"
            ) {
                val nutritionInfo = recipeViewModel.getRecipeNutrients()
                RecipeNutritionScreen(nutritionInfo)
            }
        })
}