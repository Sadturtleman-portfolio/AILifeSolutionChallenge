package com.example.barcodescanner.data.repository.impl

import android.util.Log
import com.example.barcodescanner.data.api.FoodNutritionIngredientApiService
import com.example.barcodescanner.data.model.IngredientModel
import com.example.barcodescanner.data.model.toIngredientModel
import com.example.barcodescanner.data.repository.iface.FoodNutritionIngredientRepository
import com.example.barcodescanner.data.response.NutritionItem
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FoodNutritionIngredientImpl @Inject constructor(
    private val apiService: FoodNutritionIngredientApiService
) : FoodNutritionIngredientRepository{
    override suspend fun getFoodNutritionIngredientByProductCode(code: String): Result<IngredientModel> {
        return runCatching {
            val response = apiService.getNutrition(reportNo = code)
            val item = response.body?.items?.item
            item?.firstOrNull()?.toIngredientModel() ?: IngredientModel()
        }.onFailure { exception ->
            Log.e("FoodNutritionIngredientRepository", exception.message.toString())
        }
    }
}