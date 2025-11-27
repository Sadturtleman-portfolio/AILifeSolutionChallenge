package com.example.barcodescanner.data.repository.impl

import android.util.Log
import com.example.barcodescanner.data.api.FoodNutritionApiService
import com.example.barcodescanner.data.repository.iface.FoodNutritionRepository
import com.example.barcodescanner.data.response.Nutrition
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FoodNutritionImpl @Inject constructor(
    private val apiService: FoodNutritionApiService
): FoodNutritionRepository {

    override suspend fun getFoodNutritionByReportNumber(reportNumber: String): Result<Nutrition> {
        return runCatching {
            val response = apiService.getFoodNutritionByReportNumber(
                reportNumber = reportNumber
            )
            val item = response.c002.row?.firstOrNull()
                ?: throw IllegalArgumentException("None Data")

            item
        }.onFailure { exception ->
            Log.e("FoodNutritionRepository", exception.message.toString())
        }
    }
}