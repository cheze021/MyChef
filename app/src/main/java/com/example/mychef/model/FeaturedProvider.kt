package com.example.mychef.model

import com.example.mychef.R
import com.example.mychef.ui.model.FeaturedListModel

object FeaturedProvider{
    val featuredRecipes = listOf(
        FeaturedListModel("Delicious Recipes", R.drawable.delicous_recipes),
        FeaturedListModel("Healthy Recipes", R.drawable.healthy_recipes),
        FeaturedListModel("Five Minutes Recipes", R.drawable.five_minute_recipes),
        FeaturedListModel("Breakfast Recipes", R.drawable.breakfast_recipes)
    )
}