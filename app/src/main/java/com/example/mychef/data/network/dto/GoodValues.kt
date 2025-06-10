package com.example.mychef.data.network.dto

import com.example.mychef.model.nutrition.GoodValues

data class GoodValuesDto (
    val title: String,
    val amount: String
) {
    fun toDomain() = GoodValues(
        title = title,
        amount = amount
    )
}