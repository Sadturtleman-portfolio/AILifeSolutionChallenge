package com.example.barcodescanner.data.repository.iface

interface GeminiRepository {
    suspend fun ask(text: String): Result<String>
}