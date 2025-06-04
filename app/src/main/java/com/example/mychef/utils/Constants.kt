package com.example.mychef.utils

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import com.example.mychef.ui.model.BottomNavItem

object Constants {
    val BottomNavItems = listOf(
        // Home screen
        BottomNavItem(
            label = "Home",
            icon = Icons.Filled.Home,
            route = "home"
        ),
        // Favorites screen
        BottomNavItem(
            label = "Favorites",
            icon = Icons.Filled.Favorite,
            route = "favorites"
        ),
        //Search screen
        BottomNavItem(
            label = "Search",
            icon = Icons.Filled.Search,
            route = "search"
        ),
        // Cart screen
        BottomNavItem(
            label = "Cart",
            icon = Icons.Filled.ShoppingCart,
            route = "cart"
        )
    )

    const val BASE_URL = "https://api.spoonacular.com/"
}