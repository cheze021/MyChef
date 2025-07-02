package com.example.mychef.ui.search

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mychef.ui.theme.quickSandFamily
import com.example.mychef.utils.Constants.dietStepperValues
import com.example.mychef.utils.StyledDarkerButton

@Composable
fun SearchScreen() {

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFF7F7))
    ) {
        item{
            DietRangeList()

            Spacer(modifier = Modifier.height(12.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                StyledDarkerButton(
                    onClick = {  },
                    text = "Start Search"
                )
            }

        }
    }
}

@Composable
fun DietStepperTextField(
    value: Int,
    onValueChange: (Int) -> Unit,
    modifier: Modifier = Modifier,
    label: String
) {
    val min = 0
    val max = 100

    var text by remember { mutableStateOf(value.toString()) }

    LaunchedEffect(value) {
        if (text != value.toString()) {
            text = value.toString()
        }
    }

    val customTextSelectionColors = TextSelectionColors(
        handleColor = Color.Transparent,
        backgroundColor = Color.Transparent
    )

    CompositionLocalProvider(LocalTextSelectionColors provides customTextSelectionColors) {
        TextField(
            value = text,
            onValueChange = {
                text = it
                val parsed = it.toIntOrNull()
                if (parsed != null && parsed in min..max) {
                    onValueChange(parsed)
                }
            },
            label = { Text(
                text = label,
                color = Color(0xFFB2574C),
                fontFamily = quickSandFamily,
                fontWeight = FontWeight.SemiBold
            ) },
            trailingIcon = {
                Column(
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.padding(end = 4.dp)
                ) {
                    IconButton(
                        onClick = {
                            if (value < max) onValueChange(value + 1)
                        },
                        modifier = Modifier.size(16.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowUp,
                            contentDescription = "Increment",
                            tint = Color(0xFF9C6C6C),
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                    IconButton(
                        onClick = {
                            if (value > min) onValueChange(value - 1)
                        },
                        modifier = Modifier.size(16.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowDown,
                            contentDescription = "Decrement",
                            tint = Color(0xFF9C6C6C),
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                }
            },
            singleLine = true,
            modifier = modifier
                .fillMaxWidth()
                .border(
                    width = 1.dp,
                    color = Color(0xFFEDE4E4),
                    shape = RoundedCornerShape(12.dp)
                ),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                disabledContainerColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                cursorColor = Color(0xFF9C6C6C),
            ),
            shape = RoundedCornerShape(12.dp),
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
        )
    }
}

@Composable
fun DietRangeItem(
    label: String,
    minValue: Int,
    maxValue: Int,
    expanded: Boolean,
    onExpandToggle: () -> Unit,
    onMinChange: (Int) -> Unit,
    onMaxChange: (Int) -> Unit
) {
    val rotation by animateFloatAsState(targetValue = if (expanded) 180f else 0f)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 6.dp)
            .clickable { onExpandToggle() },
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFFFFAF9)
        ),
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(0.8.dp, Color(0xFFEADDD8)),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .animateContentSize()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = label,
                    letterSpacing = 1.sp,
                    fontSize = if (expanded) 16.sp else 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFE07061),
                    fontFamily = quickSandFamily
                )

                Icon(
                    imageVector = Icons.Default.ExpandMore,
                    contentDescription = "Expand/collapse",
                    modifier = Modifier
                        .rotate(rotation)
                        .size(20.dp),
                    tint = Color(0xFFE07061)
                )
            }

            AnimatedVisibility(
                visible = expanded,
                enter = fadeIn() + expandVertically(),
                exit = fadeOut() + shrinkVertically()
            ) {
                Column {
                    DietStepperTextField(
                        value = minValue,
                        onValueChange = onMinChange,
                        label = "Min value",
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    DietStepperTextField(
                        value = maxValue,
                        onValueChange = onMaxChange,
                        label = "Max value",
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }
}

@Composable
fun DietRangeList() {
    val nutrientRanges = remember {
        mutableStateMapOf<String, Pair<Int, Int>>().apply {
            dietStepperValues.forEach { put(it, 0 to 100) }
        }
    }

    var expandedItem by rememberSaveable { mutableStateOf<String?>(null) }

    dietStepperValues.forEach { nutrient ->
        val (min, max) = nutrientRanges[nutrient] ?: (0 to 100)

        DietRangeItem(
            label = nutrient,
            minValue = min,
            maxValue = max,
            expanded = expandedItem == nutrient,
            onExpandToggle = {
                expandedItem = if (expandedItem == nutrient) null else nutrient
            },
            onMinChange = { newMin ->
                nutrientRanges[nutrient] = newMin to (nutrientRanges[nutrient]?.second ?: 0)
            },
            onMaxChange = { newMax ->
                nutrientRanges[nutrient] = (nutrientRanges[nutrient]?.first ?: 0) to newMax
            }
        )
    }

}