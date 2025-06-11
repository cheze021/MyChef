package com.example.mychef.model.nutrition

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class NutritionInfo(
    val calories: String,
    val carbs: String,
    val fat: String,
    val protein: String,
    val bad: List<BadValues>,
    val good: List<GoodValues>
) : Parcelable