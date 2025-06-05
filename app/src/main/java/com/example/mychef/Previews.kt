package com.example.mychef

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.mychef.navigation.NavGraph
import com.example.mychef.ui.home.HomeScreen
import com.example.mychef.ui.recipe_detail.RecipeDetailScreen

@Composable
@Preview(showBackground = true)
fun HomePreview() {
    val navController = rememberNavController()
    HomeScreen(navController)
}

@Composable
@Preview(showBackground = true)
fun RecipeDetailPreview() {
    RecipeDetailScreen()
}