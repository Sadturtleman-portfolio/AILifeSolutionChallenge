package com.example.barcodescanner.data.api

import com.example.barcodescanner.BuildConfig
import com.example.barcodescanner.data.response.FoodSafetyResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface BarcodeDrugApiService{
    // 바코드를 통한 품목 파악

    @GET("api/{apiKey}/{serviceId}/{type}/{startIdx}/{endIdx}/BAR_CD={barcode}")
    suspend fun getFoodByBarcode(
        @Path("apiKey") apiKey: String = BuildConfig.DRUG_BARCODE_SAFETY_API_KEY,
        @Path("serviceId") serviceId: String = "C005",
        @Path("type") type: String = "json",
        @Path("startIdx") startIdx: Int = 1,
        @Path("endIdx") endIdx: Int = 5,
        @Path("barcode") barcode: String
    ): FoodSafetyResponse
}