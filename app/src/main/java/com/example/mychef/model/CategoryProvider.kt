package com.example.mychef.model

import com.example.mychef.R

object CategoryProvider {
    val categories = listOf(
        Category(1, "main course", R.drawable.dinner_cat),
        Category(2, "dessert", R.drawable.desserts_cat),
        Category(3, "salad", R.drawable.lunch_cat),
        Category(4, "breakfast", R.drawable.breakfast_cat),
        Category(5, "snack", R.drawable.snack_cat),
        Category(6, "drinks", R.drawable.drink_cat)
    )
}