package com.example.mychef.presentation.navigation


import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.NavGraphBuilder
import com.example.mychef.presentation.home.HomeScreen


fun NavGraphBuilder.NavigationGraph(navController: NavHostController) {
    composable("home") {
        HomeScreen()
    }
    // Agregar más pantallas aquí: "detail", "favorites", etc.
}