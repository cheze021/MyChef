package com.example.mychef.presentation.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults

import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mychef.R
import com.example.mychef.domain.model.FeaturedListModel


@Composable
fun HomeScreen() {
    Scaffold(
        topBar = {
            TopAppBar()
        },
        bottomBar = {
            //BottomNavigationBar()
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            FeaturedRecipes()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar(){
    CenterAlignedTopAppBar(
        title = { Text("Home", fontWeight = FontWeight.Bold) },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = Color.White
        )
    )
}

@Composable
fun FeaturedRecipes() {
    val featuredRecipes = listOf(
        FeaturedListModel("Delicious Recipes", R.drawable.delicous_recipes),
        FeaturedListModel("Healthy Recipes", R.drawable.healthy_recipes),
        FeaturedListModel("Five Minutes Recipes", R.drawable.five_minute_recipes),
        FeaturedListModel("Breakfast Recipes", R.drawable.breakfast_recipes)
    )

    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "Featured",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 8.dp),
            fontSize = 22.sp
        )

        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            itemsIndexed(featuredRecipes) { _ , item ->
                Card(
                    modifier = Modifier
                        .width(200.dp)
                        .height(200.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White
                    ),
                    elevation = CardDefaults.cardElevation(6.dp)
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Image(
                            painter = painterResource(id = item.recepieImage),
                            contentDescription = item.recepieName,
                            modifier = Modifier
                                .height(165.dp)
                                .width(200.dp),
                            contentScale = ContentScale.Crop
                        )
                        Text(
                            text = item.recepieName,
                            modifier = Modifier
                                .padding(8.dp)
                                .fillMaxWidth(),
                            style = MaterialTheme.typography.bodyMedium,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    }
}
