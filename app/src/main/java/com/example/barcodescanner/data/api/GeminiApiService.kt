package com.example.barcodescanner.data.api


import com.example.barcodescanner.BuildConfig
import com.example.barcodescanner.data.request.GeminiRequest
import com.example.barcodescanner.data.response.GeminiResponse
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface GeminiApiService
{

    @POST("v1beta/models/{model}:generateContent")
    suspend fun generateText(
        @Path("model") model: String = "gemini-2.5-flash",
        @Query("key") apiKey: String = BuildConfig.GEMINI_API_KEY,
        @Body body: GeminiRequest
    ): GeminiResponse
}