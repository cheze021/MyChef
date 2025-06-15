package com.example.mychef

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.sharp.Favorite
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.mychef.navigation.NavGraph
import com.example.mychef.ui.theme.MyChefTheme
import com.example.mychef.ui.theme.quickSandFamily
import com.example.mychef.utils.Constants
import com.example.mychef.utils.GenericAlertDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyChefTheme {
                val navController = rememberNavController()

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.White
                ) {
                    Scaffold(
                        // Top App Bar
                        topBar = {
                          TopAppBar(navController = navController)
                        },
                        // Bottom navigation
                        bottomBar = {
                            BottomNavigationBar(navController = navController)
                        }, content = { padding ->
                            // Nav host: where screens are placed
                            NavGraph(navController = navController, padding = padding)
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    NavigationBar(
        containerColor = Color.White,
        tonalElevation = 12.dp
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        Constants.BottomNavItems.forEach { navItem ->
            NavigationBarItem(
                selected = currentRoute == navItem.route,
                onClick = {
                    navController.navigate(navItem.route)
                },
                icon = {
                    Icon(
                        imageVector = navItem.icon,
                        contentDescription = navItem.label
                    )
                },
                label = {
                    Text(
                        text = navItem.label,
                        fontFamily = quickSandFamily,
                        fontSize = 12.sp
                    )
                },
                alwaysShowLabel = true,
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color(0xFF9F7165),
                    unselectedIconColor = Color.Black ,
                    selectedTextColor = Color(0xFF9F7165),
                    unselectedTextColor = Color.Black,
                    indicatorColor = Color.Transparent
                )
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar(navController: NavHostController){
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val backArrowScreens = listOf("recipesByCategory/{category}", "recipeDetail/{recipeId}", "recipeNutrition")
    val isBackArrowVisible = currentRoute in backArrowScreens
    val isFavoritesButtonVisible = currentRoute == "recipeDetail/{recipeId}"
    val arguments = navBackStackEntry?.arguments
    val category = arguments?.getString("category")

    var isFavorite by remember { mutableStateOf(false) }
    var showDialog by remember { mutableStateOf(false) }


    val screenTitle = when (currentRoute) {
        "home" -> "Home"
        "favorites" -> "Favorites"
        "search" -> "Search"
        "cart" -> "Cart"
        "recipesByCategory/{category}" -> category?.replaceFirstChar { it.uppercase() } ?: "Category"
        "recipeNutrition" -> "Nutrition Breakdown"
        else -> ""
    }

    if (showDialog) {
        if(isFavorite){
            GenericAlertDialog(
                title = "Added to favorites!",
                icon = Icons.Default.CheckCircle,
                message = "Recipe has been added to favorites successfully",
                confirmText = "Go to favorites",
                dismissText = "Ok",
                onConfirm = {
                    showDialog = false
                    // Acción de confirmación
                },
                onDismiss = {
                    showDialog = false
                },
                isFavorite = isFavorite
            )
        } else {
            GenericAlertDialog(
                title = "Deleted from favorites :(",
                icon = Icons.Default.Delete,
                message = "Oh... recipe has been deleted from your favorites",
                confirmText = "",
                dismissText = "Ok",
                onConfirm = {
                    showDialog = false
                    // Acción de confirmación
                },
                onDismiss = {
                    showDialog = false
                },
                isFavorite = isFavorite
            )
        }
    }

    CenterAlignedTopAppBar(
        title = { Text(
            text = screenTitle,
            fontFamily = quickSandFamily,
            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
        ) },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = Color(0xFFFFF8F7)
        ),
        navigationIcon = {
            if (isBackArrowVisible) {
                IconButton(
                    modifier = Modifier
                        .padding(6.dp)
                        .size(40.dp)
                        .clip(CircleShape) // Más redondeado
                        .background(Color(0x1EC5847C)),
                    onClick = { navController.popBackStack() }) {
                    Icon(
                        Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        tint = Color(0xC3F59386),
                    )
                }
            }
        },
        actions = {
            if(isFavoritesButtonVisible) {
                IconButton(onClick = {
                    showDialog = true
                    isFavorite = !isFavorite
                }) {
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .border(
                                width = 2.dp,
                                color = if (isFavorite) Color(0xFFE57373) else Color.Gray,
                                shape = CircleShape
                            )
                            .padding(8.dp) // Espaciado interno para centrar el ícono
                    ) {
                        Icon(
                            imageVector = Icons.Sharp.Favorite, // Podés usar otro ícono si querés
                            contentDescription = "Favorites",
                            tint = if(isFavorite) Color(0xFFE57373) else Color.Gray
                        )
                    }
                }
            }
        }
    )
}

