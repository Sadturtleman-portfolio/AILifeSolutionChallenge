package com.example.barcodescanner.data.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class ResultMessage(
    @SerialName("MSG") val message: String? = null,
    @SerialName("CODE") val code: String? = null
)

@Serializable
data class CResponse<T>(
    @SerialName("total_count") val totalCount: String? = null,
    @SerialName("row") val row: List<T>? = null,
    @SerialName("RESULT") val result: ResultMessage? = null
)