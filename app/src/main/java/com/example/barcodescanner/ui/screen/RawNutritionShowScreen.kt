package com.example.barcodescanner.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.barcodescanner.data.model.NutritionModel
import com.example.barcodescanner.ui.component.NutritionList
import com.example.barcodescanner.ui.state.NutritionState
import com.example.barcodescanner.ui.viewmodel.NutritionViewModel

@Composable
fun RawNutritionShowScreen(
    productCode: String,
    nutritionViewModel: NutritionViewModel = hiltViewModel(),
    modifier: Modifier = Modifier
) {
    val state by nutritionViewModel.nutritionState.collectAsState()

    Column(
        modifier = modifier
    ) {
        when (state){
            is NutritionState.Idle -> {
                nutritionViewModel.findNutritionByCode(productCode)
            }
            is NutritionState.Loading -> {
                CircularProgressIndicator()
            }
            is NutritionState.Success -> {
                NutritionList(
                    food = NutritionModel(
                        foodName = (state as NutritionState.Success).nutritionList.foodName,
                        nutritionList = (state as NutritionState.Success).nutritionList.nutritionList
                    )
                )
            }
            is NutritionState.Error -> {
                Text(
                    (state as NutritionState.Error).message
                )
            }
        }
    }
}