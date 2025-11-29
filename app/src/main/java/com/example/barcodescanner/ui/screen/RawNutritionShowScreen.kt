package com.example.barcodescanner.ui.screen

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.barcodescanner.data.model.LocalTTS
import com.example.barcodescanner.data.model.NutritionFactItem
import com.example.barcodescanner.data.model.NutritionModel
import com.example.barcodescanner.data.model.parseNutritionJson
import com.example.barcodescanner.data.model.readText
import com.example.barcodescanner.ui.state.GeminiUiState
import com.example.barcodescanner.ui.state.NutritionState
import com.example.barcodescanner.ui.viewmodel.GeminiViewModel
import com.example.barcodescanner.ui.viewmodel.NutritionViewModel


@Composable
fun RawNutritionShowScreen(
    modifier: Modifier = Modifier,
    productCode: String,
    onBack: () -> Unit,
    nutritionViewModel: NutritionViewModel = hiltViewModel(),
    geminiViewModel: GeminiViewModel = hiltViewModel()
) {
    BackHandler {
        onBack()
    }

    val nutritionState by nutritionViewModel.nutritionState.collectAsState()
    val geminiState by geminiViewModel.state.collectAsState()
    val tts = LocalTTS.current
    // 📌 화면 최초 진입 TTS
    LaunchedEffect(Unit) {
        tts?.readText("제품을 스캔중입니다.")
    }

    Column(
        modifier = modifier
            .padding(16.dp)
            .semantics(mergeDescendants = true) {}
    ) {

        when (nutritionState) {

            is NutritionState.Idle -> {
                nutritionViewModel.findNutritionByCode(productCode)
            }

            is NutritionState.Loading -> {
                CircularProgressIndicator(
                    modifier = Modifier.semantics {
                        contentDescription = "영양 정보를 불러오는 중"
                    }
                )
            }

            is NutritionState.Success -> {

                // 📌 원재료 표시 직전 TTS

                val data = nutritionState as NutritionState.Success
                val productName = data.nutritionList.foodName

                tts.readText("$productName 의 원재료입니다.")
                NutritionListAccessible(
                    food = NutritionModel(
                        foodName = productName,
                        nutritionList = data.nutritionList.nutritionList
                    )
                )

                // Gemini 호출
                LaunchedEffect(productName) {
                    if (productName.isNotBlank()) {
                        geminiViewModel.fetchNutrition(productName)
                    }
                }

                // Gemini 결과
                GeminiNutritionSection(geminiState)
            }

            is NutritionState.Error -> {
                tts.readText("원재료 정보를 불러오지 못했습니다.")

                Text(
                    text = (nutritionState as NutritionState.Error).message,
                    fontSize = 22.sp,
                    modifier = Modifier.semantics {
                        contentDescription = "오류: ${(nutritionState as NutritionState.Error).message}"
                    }
                )
            }
        }
    }
}


@Composable
fun NutritionListAccessible(food: NutritionModel) {
    Column(
        modifier = Modifier
            .padding(12.dp)
            .semantics { heading() }
    ) {
        Text(
            text = food.foodName,
            fontSize = 28.sp,
            modifier = Modifier.semantics {
                contentDescription = "제품명: ${food.foodName}"
            }
        )

        Spacer(Modifier.height(12.dp))

        Column {
            food.nutritionList.forEach { ingredient ->
                Text(
                    text = ingredient,
                    fontSize = 22.sp,
                    modifier = Modifier
                        .padding(vertical = 6.dp)
                        .semantics {
                            contentDescription = ingredient
                        }
                )
            }
        }
    }
}

@Composable
fun GeminiNutritionSection(state: GeminiUiState) {
    val tts = LocalTTS.current
    when (state) {

        is GeminiUiState.Idle -> {}

        is GeminiUiState.Loading -> {
            CircularProgressIndicator(
                modifier = Modifier.semantics {
                    contentDescription = "AI 분석 중"
                }
            )
        }

        is GeminiUiState.Success -> {
            // TTS
            tts?.readText("AI 분석 영양성분표가 준비되었습니다.")

            // JSON 정제
            val cleanJson = remember(state.json) { sanitizeJson(state.json) }

            Log.d("food", "Cleaned JSON:\n$cleanJson")

            // 파싱
            val (facts, allergens) = remember(cleanJson) {
                parseNutritionJson(cleanJson)
            }

            AllergenSection(allergens)
            NutritionFactsSection(facts)
        }

        is GeminiUiState.Error -> {
            tts?.readText("AI 분석에 실패했습니다.")

            Text(
                text = "AI 분석 실패: ${state.message}",
                fontSize = 22.sp,
                modifier = Modifier.semantics {
                    contentDescription = "AI 분석 실패: ${state.message}"
                }
            )
        }
    }
}

fun sanitizeJson(raw: String): String {
    var clean = raw.trim()

    clean = clean
        .replace("```json", "")
        .replace("```JSON", "")
        .replace("```", "")
        .trim()

    // JSON 시작 부분만 추출
    val start = clean.indexOf('{')
    if (start > 0) clean = clean.substring(start)

    // JSON 마지막 } 이후 쓰레기 제거
    val end = clean.lastIndexOf('}')
    if (end > 0) clean = clean.take(end + 1)

    return clean.trim()
}

@Composable
fun NutritionFactsSection(facts: List<NutritionFactItem>) {

    LazyColumn(
        modifier = Modifier
            .padding(16.dp)
            .semantics { heading() }
    ) {
        item {
            Text(
                "AI 영양성분표",
                fontSize = 26.sp,
                modifier = Modifier
                    .padding(bottom = 12.dp)
                    .semantics {
                        heading()
                        contentDescription = "AI 영양성분표"
                    }
            )
        }

        items(facts.size) { index ->
            val fact = facts[index]

            Row(
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .semantics {
                        contentDescription = "${fact.name} ${fact.value}"
                    }
            ) {
                Text(
                    fact.name,
                    fontSize = 22.sp,
                    modifier = Modifier.semantics {
                        contentDescription = fact.name
                    }
                )
                Spacer(Modifier.weight(1f))
                Text(
                    fact.value,
                    fontSize = 22.sp,
                    modifier = Modifier.semantics {
                        contentDescription = fact.value
                    }
                )
            }
        }
    }
}




@Composable
fun AllergenSection(allergens: List<String>) {
    val tts = LocalTTS.current

    // 🔊 알레르기 전체 목록을 한 번에 읽어주기
    LaunchedEffect(allergens) {
        if (allergens.isEmpty()) {
            tts?.readText("알레르기 유발 성분이 없습니다.")
        } else {
            val listText = allergens.joinToString(", ")
            tts?.readText("알레르기 유발 성분은 $listText 입니다.")
        }
    }

    if (allergens.isEmpty()) return

    LazyColumn(
        modifier = Modifier
            .padding(16.dp)
            .semantics { heading() }
    ) {
        item {
            Text(
                "알레르기 유발 성분",
                fontSize = 26.sp,
                modifier = Modifier
                    .semantics {
                        heading()
                        contentDescription = "알레르기 유발 성분 목록"
                    }
            )

            Spacer(Modifier.height(8.dp))
        }

        items(allergens.size) { index ->
            val item = allergens[index]

            Text(
                text = "• $item",
                fontSize = 22.sp,
                modifier = Modifier
                    .padding(vertical = 4.dp)
                    .semantics {
                        contentDescription = "$item 포함"
                    }
            )
        }
    }
}


