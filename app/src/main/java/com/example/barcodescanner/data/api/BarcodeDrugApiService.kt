package com.example.barcodescanner.data.api

import com.example.barcodescanner.BuildConfig
import com.example.barcodescanner.data.response.BarcodeDrugApiResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface BarcodeDrugApiService{
    // 바코드를 통한 품목 파악

    @GET("/api/{apiKey}/{serviceId}/{type}/{startIdx}/{endIdx}")
    suspend fun getFoodByBarcode(
        @Path("apiKey") apiKey: String = BuildConfig.DRUG_BARCODE_SAFETY_API_KEY,
        @Path("serviceId") serviceId: String = "C005",
        @Path("type") type: String = "json",
        @Path("startIdx") startIdx: String = "1'",
        @Path("endIdx") endIdx: String = "5",
        @Query("CHNG_DT") changeDate: String? = null,
        @Query("PRDLST_REPORT_NO") reportNumber: String? = null,
        @Query("BAR_CD") barcode: String? = null
    ) : BarcodeDrugApiResponse
}