package com.example.barcodescanner.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.barcodescanner.data.model.IngredientModel
import com.example.barcodescanner.data.repository.iface.FoodNutritionIngredientRepository
import com.example.barcodescanner.data.response.NutritionItem
import com.example.barcodescanner.ui.state.IngredientState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FoodNutritionIngredientViewModel @Inject constructor(
    private val repository: FoodNutritionIngredientRepository
) : ViewModel() {

    private val _ingredientState = MutableStateFlow<IngredientState>(
        IngredientState.Idle(
            IngredientModel()
        )
    )

    val ingredientState: StateFlow<IngredientState> = _ingredientState

    fun getFoodIngredientByCode(code: String) {
        _ingredientState.value = IngredientState.Loading(null)

        viewModelScope.launch {
            repository.getFoodNutritionIngredientByProductCode(code)
                .onSuccess { response ->
                    _ingredientState.value = IngredientState.Success(
                        ingredient = response
                    )
                }
                .onFailure { exception ->
                    _ingredientState.value = IngredientState.Error(
                        ingredient = null,
                        message = exception.message ?: "Unknown Error"
                    )
                }
        }
    }
}