package com.example.barcodescanner.ui.state

import com.example.barcodescanner.data.response.BarcodeFoodItem

sealed class BarcodeDrugState(
    open val barcode: String = ""
) {
    data class Idle(override val barcode: String = "") : BarcodeDrugState(barcode)
    data class Loading(override val barcode: String) : BarcodeDrugState(barcode)
    data class Success(
        override val barcode: String,
        val data: BarcodeFoodItem
    ) : BarcodeDrugState(barcode)

    data class Error(
        override val barcode: String,
        val message: String
    ) : BarcodeDrugState(barcode)
}
