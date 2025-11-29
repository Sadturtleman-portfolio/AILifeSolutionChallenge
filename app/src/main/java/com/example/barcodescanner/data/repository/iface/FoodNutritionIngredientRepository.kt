package com.example.barcodescanner.data.repository.iface

import com.example.barcodescanner.data.model.IngredientModel

interface FoodNutritionIngredientRepository {
    suspend fun getFoodNutritionIngredientByProductCode(code: String): Result<IngredientModel>
}