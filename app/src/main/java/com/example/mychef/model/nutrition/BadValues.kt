package com.example.mychef.model.nutrition

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class BadValues(
    val title: String,
    val amount: String
) : Parcelable
