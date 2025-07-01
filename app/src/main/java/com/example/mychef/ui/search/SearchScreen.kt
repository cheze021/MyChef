package com.example.mychef.ui.search

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
            val dietValues = remember {
                mutableStateMapOf<String, Int>().apply {
                    dietStepperValues.forEach { put(it, 0) }
                }
            }

            Column(modifier = Modifier.padding(16.dp)) {
                dietStepperValues.forEach { label ->
                    Spacer(modifier = Modifier.height(10.dp))

                    DietStepperTextField(
                        value = dietValues[label] ?: 1,
                        onValueChange = { newValue ->
                            dietValues[label] = newValue
                        },
                        modifier = Modifier.fillMaxWidth(),
                        label = label
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

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
                color = Color(0xFFE07061),
                fontFamily = quickSandFamily
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