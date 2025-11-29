package com.example.barcodescanner.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.barcodescanner.data.model.NutritionModel
import com.example.barcodescanner.data.repository.iface.FoodNutritionRepository
import com.example.barcodescanner.ui.state.NutritionState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NutritionViewModel @Inject constructor(
    private val nutritionRepository: FoodNutritionRepository
): ViewModel() {
    private val _nutritionState = MutableStateFlow<NutritionState>(NutritionState.Idle())
    val nutritionState: StateFlow<NutritionState> = _nutritionState
    fun reset() {
        _nutritionState.value = NutritionState.Idle()
    }
    fun findNutritionByCode(code: String){
        _nutritionState.value = NutritionState.Loading()
        viewModelScope.launch {
            nutritionRepository.getFoodNutritionByReportNumber(code)
                .onSuccess { response ->
                    _nutritionState.value = NutritionState.Success(
                        nutritionList = NutritionModel(
                            foodName = response.name ?: "",
                            nutritionList = response.rawNutrition?.split(" ") ?: emptyList()
                        )
                    )
                }
                .onFailure { exception ->
                    _nutritionState.value = NutritionState.Error(
                        nutritionList = NutritionModel(
                            foodName = "",
                            nutritionList = emptyList()
                        ),
                        message = exception.message ?: "Unknown Error"
                    )
                }
        }
    }
}