package com.example.mychef.model

import com.example.mychef.R

object CategoryProvider {
    val categories = listOf(
        Category(1, "Main Course", R.drawable.dinner_cat),
        Category(2, "Dessert", R.drawable.desserts_cat),
        Category(3, "Salad", R.drawable.lunch_cat),
        Category(4, "Breakfast", R.drawable.breakfast_cat),
        Category(5, "Snack", R.drawable.snack_cat),
        Category(6, "Drinks", R.drawable.drink_cat)
    )
}