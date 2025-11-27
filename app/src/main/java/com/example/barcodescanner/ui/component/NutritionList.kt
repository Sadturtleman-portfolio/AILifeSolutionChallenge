package com.example.barcodescanner.ui.component

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.barcodescanner.data.model.NutritionModel

@Composable
fun NutritionList(food: NutritionModel, modifier: Modifier = Modifier){
    LazyColumn(
        modifier = modifier
    ) {
        item {
            Text(food.foodName)
        }

        items(food.nutritionList){ nutrition ->
            Text(nutrition)
        }
    }
}