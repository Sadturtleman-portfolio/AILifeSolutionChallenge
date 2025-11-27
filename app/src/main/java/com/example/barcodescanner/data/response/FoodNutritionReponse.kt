package com.example.barcodescanner.data.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FoodNutritionResponse(
    @SerialName("C002") val c002: CResponse<Nutrition>
)

@Serializable
data class Nutrition(
    @SerialName("LCNS_NO") val acceptNUmber: String? = null,
    @SerialName("BSSH_NM") val companyName: String? = null,
    @SerialName("PRDLST_REPORT_NO") val reportNumber: String? = null,
    @SerialName("PRMS_DT") val reportDate: String? = null,
    @SerialName("PRDLST_NM") val name: String? = null,
    @SerialName("PRDLST_DCNM") val category: String? = null,
    @SerialName("RAWMTRL_NM") val rawNutrition: String? = null,
    @SerialName("CHNG_DT") val changeDate: String? = null
)