package com.example.mychef.ui.recipe_detail

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.mychef.model.nutrition.BadValues
import com.example.mychef.model.nutrition.GoodValues
import com.example.mychef.model.nutrition.NutritionInfo
import com.example.mychef.ui.theme.quickSandFamily

@Composable
fun RecipeNutritionScreen(
    nutritionInfo: NutritionInfo?
) {

    var visible by remember {
        mutableStateOf(true)
    }
    val scrollState = rememberScrollState()

    AnimatedVisibility(
        visible = visible,
        enter = fadeIn(
            // Fade in with the initial alpha of 0.3f.
            initialAlpha = 0.3f
        ),
        exit = fadeOut()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFFFF8F7))

        ) {
            if(nutritionInfo != null) {
                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    NutritionGrid(nutritionInfo)
                }

                Column(
                    modifier = Modifier
                        .padding(10.dp)
                        .verticalScroll(scrollState)
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    NutritionHealthyValues(nutritionInfo.good)

                    Spacer(modifier = Modifier.height(4.dp))

                    NutritionUnhealthyValues(nutritionInfo.bad)
                }
            }
        }
    }

}

@Composable
fun NutritionValues(title: String, value: List<Pair<String, String>>){
    Column(
        modifier = Modifier
            .padding(10.dp)
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
            fontFamily = quickSandFamily,
            color = Color(0xFF5E504A),
        )

        value.forEach { (name, amount) ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = name,
                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Medium),
                    fontFamily = quickSandFamily,
                    color = Color(0xFFE07061)
                )
                Text(
                    text = amount,
                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.SemiBold),
                    fontWeight = FontWeight.Medium,
                    fontFamily = quickSandFamily,
                    color = Color(0xFF2B2B2B)
                )
            }
        }
    }
}


@Composable
fun NutritionUnhealthyValues(badValuesList: List<BadValues>) {
    val value = badValuesList.map { it.title to it.amount }
    NutritionValues(title = "Less Healthy Nutrients", value = value)
}

@Composable
fun NutritionHealthyValues(goodValuesList: List<GoodValues>) {
    val value = goodValuesList.map { it.title to it.amount }
    NutritionValues(title = "Healthy Nutrients", value = value)
}

@Composable
fun ComunNutritionCard(
    title: String,
    value: String
) {
    Card(
        modifier = Modifier
            .padding(6.dp)
            .width(150.dp)
            .height(90.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFFFFAF9)
        ),
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(0.8.dp, Color(0xFFEADDD8)),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                fontFamily = quickSandFamily,
                color = Color(0xFFE07061)
            )
            Text(
                text = value,
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold),
                fontFamily = quickSandFamily,
                color = Color(0xFF2B2B2B)
            )
        }
    }
}

@Composable
fun NutritionGrid(nutritionInfo: NutritionInfo) {
    val items = listOf(
        "Calories" to nutritionInfo.calories,
        "Fat" to nutritionInfo.fat,
        "Carbs" to nutritionInfo.carbs,
        "Protein" to nutritionInfo.protein
    )

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier
            .padding(10.dp)
            .height(210.dp),
        verticalArrangement = Arrangement.spacedBy(2.dp),
        horizontalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        items(items.size) { index ->
            val (title, value) = items[index]
            ComunNutritionCard(title = title, value = value)
        }
    }
}