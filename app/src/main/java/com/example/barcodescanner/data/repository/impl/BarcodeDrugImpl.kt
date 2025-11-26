package com.example.barcodescanner.data.repository.impl

import android.util.Log
import com.example.barcodescanner.data.api.BarcodeDrugApiService
import com.example.barcodescanner.data.repository.iface.BarcodeDrugRepository
import com.example.barcodescanner.data.response.BarcodeDrugApiResponse
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BarcodeDrugImpl @Inject constructor(
    private val apiService: BarcodeDrugApiService
) : BarcodeDrugRepository {
    override suspend fun getFoodByBarcode(
        barcodeNumber: String
    ): Result<BarcodeDrugApiResponse> {
        return runCatching {
            val response = apiService.getFoodByBarcode(
                barcode = barcodeNumber
            )
            response
        }.onFailure { exception ->
            Log.e("BarcodeDrugRepository", exception.message.toString(), exception)
        }
    }
}