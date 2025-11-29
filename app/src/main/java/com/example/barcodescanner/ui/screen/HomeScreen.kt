package com.example.barcodescanner.ui.screen

import android.speech.tts.TextToSpeech
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.barcodescanner.data.model.LocalTTS
import com.example.barcodescanner.data.model.readText
import com.example.barcodescanner.ui.state.BarcodeDrugState
import com.example.barcodescanner.ui.viewmodel.BarcodeViewModel
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.codescanner.GmsBarcodeScannerOptions
import com.google.mlkit.vision.codescanner.GmsBarcodeScanning
import java.util.Locale


// ------------------------------------------------------
// 🏠 2. HomeScreen 전체
// ------------------------------------------------------
@Composable
fun HomeScreen(
    onScanClick: (String) -> Unit,
    modifier: Modifier = Modifier,
    barcodeDrugViewModel: BarcodeViewModel = hiltViewModel()
){
    val context = LocalContext.current
    val state by barcodeDrugViewModel.barcodeDrugState.collectAsState()

    val scannerOptions = GmsBarcodeScannerOptions.Builder()
        .setBarcodeFormats(Barcode.FORMAT_EAN_13)
        .enableAutoZoom()
        .build()

    val scanner = GmsBarcodeScanning.getClient(context, scannerOptions)

    when (state) {

        // ------------------------------------------------------
        // 📌 Idle 상태 — 스캔 준비 화면 + TTS 안내
        // ------------------------------------------------------
        is BarcodeDrugState.Idle -> {
            IdleStateHomeScreen(
                modifier = modifier,
                onScanClick = {
                    scanner.startScan()
                        .addOnSuccessListener { barcode ->
                            val raw = barcode.rawValue ?: ""
                            barcodeDrugViewModel.scanBarcode(raw)
                        }
                        .addOnFailureListener { exception ->
                            barcodeDrugViewModel.scanFailed(exception.message)
                        }
                }
            )
        }

        // ------------------------------------------------------
        // ⏳ 로딩 중
        // ------------------------------------------------------
        is BarcodeDrugState.Loading -> {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .semantics {
                        contentDescription = "스캔한 바코드를 조회하고 있습니다"
                    },
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        // ------------------------------------------------------
        // 🎉 성공 — 다음 화면으로 이동
        // ------------------------------------------------------
        is BarcodeDrugState.Success -> {
            onScanClick((state as BarcodeDrugState.Success).data.reportNumber ?: "")
            barcodeDrugViewModel.reset()
        }

        // ------------------------------------------------------
        // ❌ 오류 화면
        // ------------------------------------------------------
        is BarcodeDrugState.Error -> {
            ErrorStateHome(
                message = (state as BarcodeDrugState.Error).message,
                barcode = (state as BarcodeDrugState.Error).barcode,
                onRetry = {
                    barcodeDrugViewModel.reset()  // Idle 상태로
                }
            )
        }

    }
}

// ------------------------------------------------------
// 🔹 3. Idle (대기) 상태 화면 (+TTS)
// ------------------------------------------------------
@Composable
private fun IdleStateHomeScreen(
    modifier: Modifier = Modifier,
    onScanClick: () -> Unit
){
    // 🔊 화면 진입 시 음성 안내
    val tts = LocalTTS.current
    
    LaunchedEffect(Unit) {
        tts?.readText("사용할 제품을 스캔하시오")
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp)
            .semantics(mergeDescendants = true) {}
            .semantics { heading() }, // TalkBack 제목 인식
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "제품 바코드를 스캔해주세요",
            fontSize = 28.sp,
            modifier = Modifier.semantics {
                contentDescription = "제품 바코드를 스캔해주세요"
            }
        )

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = onScanClick,
            modifier = Modifier
                .padding(12.dp)
                .semantics {
                    contentDescription = "바코드 스캔 버튼"
                }
        ) {
            Text(
                "스캔 시작",
                fontSize = 26.sp
            )
        }
    }
}

// ------------------------------------------------------
// ❌ 4. 에러 화면
// ------------------------------------------------------
@Composable
private fun ErrorStateHome(
    message: String,
    barcode: String,
    onRetry: () -> Unit
) {
    val tts = LocalTTS.current

    LaunchedEffect(Unit) {
        tts?.readText("오류가 발생했습니다. 초기화면으로 돌아가세요")
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
            .semantics(mergeDescendants = true) {},
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "오류가 발생했습니다",
            fontSize = 26.sp,
            modifier = Modifier.semantics { heading() }
        )

        Spacer(Modifier.height(16.dp))

        Text(
            text = message,
            fontSize = 22.sp,
            modifier = Modifier.semantics {
                contentDescription = "오류 메시지: $message"
            }
        )

        Spacer(Modifier.height(8.dp))

        Text(
            text = "스캔한 바코드: $barcode",
            fontSize = 22.sp,
            modifier = Modifier.semantics {
                contentDescription = "스캔한 바코드 번호는 $barcode 입니다"
            }
        )

        Spacer(Modifier.height(24.dp))

        Button(
            onClick = onRetry,
            modifier = Modifier.semantics { contentDescription = "다시 스캔하기 버튼" }
        ) {
            Text("다시 스캔하기", fontSize = 24.sp)
        }
    }
}
