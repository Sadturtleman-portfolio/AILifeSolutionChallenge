package com.example.barcodescanner.data.repository.impl

import android.util.Log
import com.example.barcodescanner.data.api.GeminiApiService
import com.example.barcodescanner.data.repository.iface.GeminiRepository
import com.example.barcodescanner.data.request.Content
import com.example.barcodescanner.data.request.GeminiRequest
import com.example.barcodescanner.data.request.Part
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GeminiImpl @Inject constructor(
    private val apiService: GeminiApiService
) : GeminiRepository {

    override suspend fun ask(text: String): Result<String> {
        return runCatching {
            val response = apiService.generateText(
                body = GeminiRequest(
                    contents = listOf(
                        Content(
                            parts = listOf(
                                Part(text)
                            )
                        )
                    )
                )
            )

            val answer = response.candidates
                .firstOrNull()
                ?.content
                ?.parts
                ?.joinToString("\n") { it.text }
                ?: "응답 없음"

            answer
        }.onFailure { e ->
            Log.e("GeminiImpl", "Error: ${e.message}")
        }
    }
}
