package com.example.barcodescanner.data.repository.iface

import com.example.barcodescanner.data.response.Nutrition

interface FoodNutritionRepository {
    suspend fun getFoodNutritionByReportNumber(
        reportNumber: String
    ): Result<Nutrition>

}