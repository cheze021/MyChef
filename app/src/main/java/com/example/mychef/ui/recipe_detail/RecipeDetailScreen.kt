package com.example.mychef.ui.recipe_detail

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.sharp.Favorite
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.mychef.model.Ingredient
import com.example.mychef.model.Recipe
import com.example.mychef.presentation.recipe.RecipeViewModel
import com.example.mychef.ui.theme.quickSandFamily
import com.example.mychef.utils.GenericAlertDialog

@Composable
fun RecipeDetailScreen(
    recipeId: Int,
    recipeViewModel: RecipeViewModel = hiltViewModel(),
    navController: NavController
) {
    val state by recipeViewModel.uiState.collectAsState()

    LaunchedEffect(recipeId) {
        recipeViewModel.loadRecipeDetail(recipeId)
        recipeViewModel.loadRecipeNutrients(recipeId)
    }

    when {
        state.isLoading -> {
            Box(Modifier
                .fillMaxSize()
                .background(Color(0xFFFFF7F7)),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        state.error != null -> {
            Box(
                Modifier
                    .fillMaxSize()
                    .background(Color(0xFFFFF7F7)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Error: ${state.error}",
                    fontFamily = quickSandFamily,
                )
            }
        }

        state.isSuccess -> {
            state.selectedRecipe?.let { recipe ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(0xFFFFF8F7))
                        .verticalScroll(rememberScrollState())
                ) {
                    AsyncImage(
                        model = recipe.imageUrl,
                        contentDescription = recipe.title,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(250.dp),
                        contentScale = ContentScale.Crop
                    )

                    Column(modifier = Modifier.padding(16.dp)) {

                        RecipeInfo(recipe, recipeViewModel, navController)

                        Spacer(modifier = Modifier.height(24.dp))

                        IngredientsInfo(recipe)

                        Spacer(modifier = Modifier.height(32.dp))

                        Button(
                            onClick = {
                                recipeViewModel.setSelectedNutritionInfo(state.recipeNutrients)
                                navController.navigate("recipeNutrition")
                            },
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

        }
        else -> {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(
                    text= "Recipe not found",
                    fontFamily = quickSandFamily
                )
            }
        }
    }
}

@Composable
fun IngredientsInfo(recipe: Recipe) {
    Text(
        text = "Ingredients",
        fontFamily = quickSandFamily,
        style = MaterialTheme.typography.headlineSmall,
        fontWeight = FontWeight.Bold
    )

    Spacer(modifier = Modifier.height(8.dp))

    IngredientsGrid(ingredients = recipe.ingredients)
}

@Composable
fun RecipeInfo(recipe: Recipe, recipeViewModel: RecipeViewModel, navController: NavController){
    var showDialog by remember { mutableStateOf(false) }
    val isInFavorites = recipeViewModel.isFavorite(recipe.id)

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Text(
            modifier = Modifier.weight(1f),
            text = recipe.title,
            fontFamily = quickSandFamily,
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.width(4.dp))

        IconButton(onClick = {
            showDialog = true
            if (isInFavorites) {
                recipeViewModel.removeFromFavorites(recipe.id)
            } else {
                recipeViewModel.addToFavorites(recipe)
            }
        }) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .border(
                        width = 2.dp,
                        color = if (isInFavorites) Color(0xFFE57373) else Color.Gray,
                        shape = CircleShape
                    )
                    .padding(8.dp)
            ) {
                Icon(
                    imageVector = Icons.Sharp.Favorite,
                    contentDescription = "Favorites",
                    tint = if(isInFavorites) Color(0xFFE57373) else Color.Gray
                )
            }
        }
    }

    Spacer(modifier = Modifier.height(16.dp))

    InfoRow(label = "Ready in", value = "${recipe.readyInMinutes} min")
    InfoRow(label = "Preparation", value = "${recipe.preparationMinutes} min")
    InfoRow(label = "Cooking", value = "${recipe.cookingMinutes} min")
    InfoRow(label = "Servings", value = recipe.servings.toString())

    if (showDialog) {
        if(isInFavorites){
            GenericAlertDialog(
                title = "Added to favorites!",
                icon = Icons.Default.CheckCircle,
                message = "Recipe has been added to favorites successfully",
                confirmText = "Go to favorites",
                dismissText = "Ok",
                onConfirm = {
                    showDialog = false
                    navController.navigate("favorites")
                },
                onDismiss = {
                    showDialog = false
                },
                isFavorite = true
            )
        } else {
            GenericAlertDialog(
                title = "Removed from favorites :(",
                icon = Icons.Default.Delete,
                message = "Oh... the recipe has been removed from your favorites",
                confirmText = "",
                dismissText = "Ok",
                onDismiss = {
                    showDialog = false
                },
                isFavorite = false
            )
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