package com.example.barcodescanner.data.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FoodSafetyResponse(
    @SerialName("C005") val c005: C005Response? = null
)

@Serializable
data class C005Response(
    @SerialName("total_count") val totalCount: String? = null,
    @SerialName("row") val row: List<BarcodeFoodItem>? = null,
    @SerialName("RESULT") val result: ResultMessage? = null
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

@Serializable
data class ResultMessage(
    @SerialName("MSG") val message: String? = null,
    @SerialName("CODE") val code: String? = null
)
