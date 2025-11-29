package com.example.barcodescanner.data.response

import kotlinx.serialization.Serializable

@Serializable
data class GeminiResponse(
    val candidates: List<Candidate>
)

@Serializable
data class Candidate(
    val content: ContentResponse
)
@Serializable
data class ContentResponse(
    val parts: List<PartResponse>
)
@Serializable
data class PartResponse(
    val text: String
)