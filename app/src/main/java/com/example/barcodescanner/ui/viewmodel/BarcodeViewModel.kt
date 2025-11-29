package com.example.barcodescanner.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.barcodescanner.data.repository.iface.BarcodeDrugRepository
import com.example.barcodescanner.ui.state.BarcodeDrugState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BarcodeViewModel @Inject constructor(
    private val barcodeDrugRepository: BarcodeDrugRepository
): ViewModel() {

    private val _barcodeDrugState = MutableStateFlow<BarcodeDrugState>(BarcodeDrugState.Idle())
    val barcodeDrugState: StateFlow<BarcodeDrugState> = _barcodeDrugState
    fun reset() {
        _barcodeDrugState.value = BarcodeDrugState.Idle()
    }
    fun scanBarcode(raw: String) {
        // 숫자만 허용
        if (!raw.all { it.isDigit() }) return

        // 길이 13자리(EAN-13)
        if (raw.length != 13) return

        // 체크섬(EAN-13) 검증
        if (!isValidEAN13(raw)) return

        // 정상 스캔 → API 요청 시작
        _barcodeDrugState.value = BarcodeDrugState.Loading(barcode = raw)
        fetchFoodInfo(raw)
    }

    fun scanFailed(message: String?) {
        _barcodeDrugState.value = BarcodeDrugState.Error(
            barcode = "",
            message = message ?: "Scan Failed"
        )
    }

    private fun fetchFoodInfo(barcode: String) {
        viewModelScope.launch {
            barcodeDrugRepository.getFoodByBarcode(barcode)
                .onSuccess { response ->
                    _barcodeDrugState.value = BarcodeDrugState.Success(
                        barcode = barcode,
                        data = response
                    )
                }
                .onFailure { exception ->
                    _barcodeDrugState.value = BarcodeDrugState.Error(
                        barcode = barcode,
                        message = exception.message ?: "Unknown Error"
                    )
                }
        }
    }

    // EAN-13 체크섬 검증
    private fun isValidEAN13(barcode: String): Boolean {
        if (barcode.length != 13 || !barcode.all { it.isDigit() }) return false

        val digits = barcode.map { it.toString().toInt() }
        val sum = digits.take(12).mapIndexed { index, d ->
            if (index % 2 == 0) d else d * 3
        }.sum()

        val checkDigit = (10 - (sum % 10)) % 10

        return checkDigit == digits[12]
    }
}
