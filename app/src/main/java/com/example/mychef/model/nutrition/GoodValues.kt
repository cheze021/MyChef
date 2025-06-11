package com.example.mychef.model.nutrition

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class GoodValues(
    val title: String,
    val amount: String
) : Parcelable