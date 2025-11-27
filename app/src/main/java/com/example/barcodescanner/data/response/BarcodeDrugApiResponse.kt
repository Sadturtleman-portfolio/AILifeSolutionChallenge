package com.example.barcodescanner.data.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class FoodSafetyResponse(
    @SerialName("C005") val c005: CResponse<BarcodeFoodItem>
)

@Serializable
data class BarcodeFoodItem(
    @SerialName("PRDLST_REPORT_NO") val reportNumber: String? = null,
    @SerialName("PRMS_DT") val reportDate: String? = null,
    @SerialName("END_DT") val endDate: String? = null,
    @SerialName("PRDLST_NM") val productName: String? = null,
    @SerialName("POG_DAYCNT") val eatDate: String? = null,
    @SerialName("BSSH_NM") val companyName: String? = null,
    @SerialName("INDUTY_NM") val industryName: String? = null,
    @SerialName("SITE_ADDR") val address: String? = null,
    @SerialName("CLSBIZ_DT") val closeDate: String? = null,
    @SerialName("BAR_CD") val barcode: String? = null
)
