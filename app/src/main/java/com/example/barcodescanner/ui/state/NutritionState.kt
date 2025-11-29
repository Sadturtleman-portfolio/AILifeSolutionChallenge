package com.example.barcodescanner.ui.state

import com.example.barcodescanner.data.model.NutritionModel

sealed class NutritionState(
    open val nutritionList: NutritionModel = NutritionModel("", emptyList())
){
    data class Idle(override val nutritionList: NutritionModel = NutritionModel("", emptyList())) : NutritionState(nutritionList)
    data class Loading(override val nutritionList: NutritionModel = NutritionModel("", emptyList())): NutritionState(nutritionList)
    data class Success(
        override val nutritionList: NutritionModel
    ): NutritionState(nutritionList)
    data class Error(
        override val nutritionList: NutritionModel,
        val message: String
    ): NutritionState(nutritionList)
}