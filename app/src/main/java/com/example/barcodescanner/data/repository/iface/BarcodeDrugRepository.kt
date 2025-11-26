package com.example.barcodescanner.data.repository.iface

import com.example.barcodescanner.data.response.BarcodeDrugApiResponse

interface BarcodeDrugRepository {
    suspend fun getFoodByBarcode(
        barcodeNumber: String
    ): Result<BarcodeDrugApiResponse>
}