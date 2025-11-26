package com.example.barcodescanner.data.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BarcodeDrugApiResponse(
    @SerialName("PRDLST_REPORT_NO") val reportNumber: String,
    @SerialName("PRMS_DT") val reportDate: String,
    @SerialName("END_DT") val endDate: String,
    @SerialName("PRDLST_NM") val productName: String,
    @SerialName("POG_DAYCNT") val productEatDate: String,
    @SerialName("BSSH_NM") val factoryName: String,
    @SerialName("INDUTY_NM") val industryName: String,
    @SerialName("SITE_ADDR") val address: String,
    @SerialName("CLSBIZ_DT") val closeDate: String,
    @SerialName("BAR_CD") val barcodeNumber: String
)
