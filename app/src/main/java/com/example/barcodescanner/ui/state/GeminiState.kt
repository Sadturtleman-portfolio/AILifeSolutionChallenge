package com.example.barcodescanner.ui.state

sealed class GeminiUiState {
    object Idle : GeminiUiState()
    object Loading : GeminiUiState()
    data class Success(val json: String) : GeminiUiState()
    data class Error(val message: String) : GeminiUiState()
}