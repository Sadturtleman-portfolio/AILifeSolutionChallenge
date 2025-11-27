package com.example.barcodescanner.data.api

import com.example.barcodescanner.BuildConfig
import com.example.barcodescanner.data.response.FoodNutritionResponse
import com.example.barcodescanner.data.response.Nutrition
import retrofit2.http.GET
import retrofit2.http.Path

interface FoodNutritionApiService {
    @GET("api/{apiKey}/{serviceId}/{type}/{startIdx}/{endIdx}/PRDLST_REPORT_NO={reportNumber}")
    suspend fun getFoodNutritionByReportNumber(
        @Path("apiKey") apiKey: String = BuildConfig.DRUG_BARCODE_SAFETY_API_KEY,
        @Path("serviceId") serviceId: String = "C002",
        @Path("type") type: String = "json",
        @Path("startIdx") startIdx: Int = 1,
        @Path("endIdx") endIdx: Int = 5,
        @Path("reportNumber") reportNumber: String
    ) : FoodNutritionResponse
}