package com.example.mychef

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.mychef.presentation.navigation.NavigationGraph

@Composable
fun MyChefApp() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        NavigationGraph(navController)
    }
}