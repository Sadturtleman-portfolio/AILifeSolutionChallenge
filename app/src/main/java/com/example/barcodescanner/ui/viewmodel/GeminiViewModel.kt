package com.example.barcodescanner.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.barcodescanner.data.repository.iface.GeminiRepository
import com.example.barcodescanner.ui.state.GeminiUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GeminiViewModel @Inject constructor(
    private val repository: GeminiRepository
) : ViewModel() {

    private val _state = MutableStateFlow<GeminiUiState>(GeminiUiState.Idle)
    val state: StateFlow<GeminiUiState> = _state
    fun reset() {
        _state.value = GeminiUiState.Idle
    }

    fun fetchNutrition(productName: String) {
        val prompt = buildNutritionPrompt(productName)

        _state.value = GeminiUiState.Loading

        viewModelScope.launch {
            repository.ask(prompt)
                .onSuccess { text ->
                    _state.value = GeminiUiState.Success(text)
                }
                .onFailure { e ->
                    _state.value = GeminiUiState.Error(e.message ?: "Unknown error")
                }
        }
    }
}

fun buildNutritionPrompt(productName: String): String {
    return """
        아래 제품의 알레르기 유발 성분과 영양성분 정보를 제공해줘.

        제품 이름: "$productName"

        반드시 아래의 JSON 형식만 사용하여 답변해.
        설명, 문장, 주석, 코드블록(\`\`\`)은 절대 포함하지 말고,
        오직 JSON 객체만 반환해야 해.

        {
          "allergens": ["성분1", "성분2", ...],
          "nutrition": {
            "sodium": "",            // mg
            "sugars": "",            // g
            "carbohydrates": "",     // g
            "fat": "",               // g
            "trans_fat": "",         // g
            "saturated_fat": "",     // g
            "cholesterol": "",       // mg
            "protein": "",           // g
            "calcium": ""            // mg
          }
        }

        지켜야 하는 규칙:
        1. 위 JSON key를 반드시 그대로 사용해.
           (sodium, sugars, carbohydrates, fat, trans_fat, saturated_fat, cholesterol, protein, calcium)
        2. JSON 구조를 변경하지 마.
        3. 값을 모르는 경우 빈 문자열("")로 둬.
        4. 어떠한 문장, 해설, 코드블록 없이 JSON만 출력해.
    """.trimIndent()
}

