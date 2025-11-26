package com.example.barcodescanner.data.repository.iface

import com.example.barcodescanner.data.response.BarcodeFoodItem

interface BarcodeDrugRepository {
    suspend fun getFoodByBarcode(
        barcodeNumber: String
    ): Result<BarcodeFoodItem>
}