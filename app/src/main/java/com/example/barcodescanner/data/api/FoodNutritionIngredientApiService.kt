package com.example.barcodescanner.data.api

import com.example.barcodescanner.BuildConfig
import com.example.barcodescanner.data.response.FoodNutritionIngredientResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface FoodNutritionIngredientApiService {
    @GET("getFoodNtrCpntDbInq02")
    suspend fun getNutrition(
        @Query("serviceKey") serviceKey: String = BuildConfig.FOOD_NUTRITION_INGREDIENT_API_KEY,
        @Query("pageNo") pageNo: Int = 1,
        @Query("numOfRows") numOfRows: Int = 1,
        @Query("type") type: String = "json",
        @Query("ITEM_REPORT_NO") reportNo: String,
        @Query("FOOD_NM_KR") foodName: String? = null,
        @Query("MAKER_NM") makerName: String? = null
    ): FoodNutritionIngredientResponse
}