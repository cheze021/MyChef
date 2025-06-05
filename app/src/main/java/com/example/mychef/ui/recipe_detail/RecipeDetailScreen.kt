package com.example.mychef.ui.recipe_detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.mychef.model.Ingredient
import com.example.mychef.model.Recipe
import com.example.mychef.ui.theme.quickSandFamily

@Composable
fun RecipeDetailScreen() {
    val fakeRecipe = Recipe(
        id = 1,
        title = "Grilled Cajun Salmon",
        imageUrl = "https://img.spoonacular.com/recipes/1684-556x370.jpg",
        readyInMinutes = 30,
        servings = 4,
        preparationMinutes = 10,
        cookingMinutes = 20,
        ingredients = listOf(
            Ingredient(1, "Ingredient 1", 2.0, "Units"),
            Ingredient(2, "Ingredient 2", 3.0, "Units"),
            Ingredient(3, "Ingredient 3", 2.5, "Units"),
            Ingredient(4, "Ingredient 4", 2.0, "Units"),
            Ingredient(5, "Ingredient 5", 1.0, "Units"),
            Ingredient(6, "Ingredient 6", 4.5, "Units"),
            Ingredient(7, "Ingredient 7", 7.0, "Units"),
            Ingredient(8, "Ingredient 7", 7.0, "Units"),
            Ingredient(9, "Ingredient 7", 7.0, "Units"),
            Ingredient(10, "Ingredient 7", 7.0, "Units"),
            Ingredient(11, "Ingredient 7", 7.0, "Units"),
            Ingredient(12, "Ingredient 8", 4.5, "Units")
        )
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFF8F7))
            .verticalScroll(rememberScrollState())
    ) {
        AsyncImage(
            model = fakeRecipe.imageUrl,
            contentDescription = fakeRecipe.title,
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp),
            contentScale = ContentScale.Crop
        )

        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = fakeRecipe.title,
                fontFamily = quickSandFamily,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(16.dp))

            InfoRow(label = "Ready in", value = "${fakeRecipe.readyInMinutes} min")
            InfoRow(label = "Preparation", value = "${fakeRecipe.preparationMinutes} min")
            InfoRow(label = "Cooking", value = "${fakeRecipe.cookingMinutes} min")
            InfoRow(label = "Servings", value = fakeRecipe.servings.toString())

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Ingredients",
                fontFamily = quickSandFamily,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            IngredientsGrid(ingredients = fakeRecipe.ingredients)

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = { /* TODO: Navegar a pasos o comenzar */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE53935)),
                shape = RoundedCornerShape(50)
            ) {
                Text(
                    text = "Check Nutritional Values",
                    color = Color.White,
                    fontFamily = quickSandFamily
                )
            }
        }
    }
}

@Composable
fun InfoRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            fontFamily = quickSandFamily,
            color = Color(0xFFE07061)
        )
        Text(
            text = value,
            fontFamily = quickSandFamily,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
fun IngredientsGrid(ingredients: List<Ingredient>) {
    val chunked = ingredients.chunked(2)
    Column {
        chunked.forEach { row ->
            Row(modifier = Modifier.fillMaxWidth()) {
                row.forEach { ingredient ->
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .padding(vertical = 8.dp)
                    ) {
                        HorizontalDivider(thickness = 2.dp, color = Color(0xADFFD1CF))
                        Text(
                            text = ingredient.name,
                            fontFamily = quickSandFamily,
                            fontWeight = FontWeight.SemiBold,
                            color = Color(0xFFE07061)
                        )
                        Text(
                            text = "${ingredient.amount} ${ingredient.unit}",
                            fontFamily = quickSandFamily,
                            fontSize = 12.sp
                        )
                    }
                }
            }
        }
    }
}