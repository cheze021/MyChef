package com.example.mychef.data.network.dto

import com.example.mychef.model.nutrition.BadValues

data class BadValuesDto (
    val title: String,
    val amount: String
) {
    fun toDomain() = BadValues(
        title = title,
        amount = amount
    )
}