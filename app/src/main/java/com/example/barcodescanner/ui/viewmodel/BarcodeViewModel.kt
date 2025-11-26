package com.example.barcodescanner.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.barcodescanner.data.repository.iface.BarcodeDrugRepository
import com.example.barcodescanner.ui.state.BarcodeDrugState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BarcodeViewModel @Inject constructor(
    private val barcodeDrugRepository: BarcodeDrugRepository
): ViewModel(){

    private val _barcodeDrugState = MutableStateFlow<BarcodeDrugState>(BarcodeDrugState.Idle())
    val barcodeDrugState: StateFlow<BarcodeDrugState> = _barcodeDrugState.asStateFlow()

    fun scanBarcode(barcode: String){
        _barcodeDrugState.value = BarcodeDrugState.Loading(barcode = barcode)
        fetchFoodInfo(barcode)
    }

    fun scanFailed(message: String?){
        _barcodeDrugState.value = BarcodeDrugState.Error(
            barcode = "",
            message = message ?: "Scan Failed"
        )
    }

    private fun fetchFoodInfo(barcode: String){
        viewModelScope.launch {
            barcodeDrugRepository.getFoodByBarcode(barcode)
                .onSuccess {response ->
                    _barcodeDrugState.value = BarcodeDrugState.Success(
                        barcode = barcode,
                        data = response
                    )
                }
                .onFailure {exception ->
                    _barcodeDrugState.value = BarcodeDrugState.Error(
                        barcode = barcode,
                        message = exception.message ?: "Unknown Error"
                    )
                }
        }
    }
}