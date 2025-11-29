package com.example.barcodescanner.ui.state

import com.example.barcodescanner.data.model.IngredientModel


sealed class IngredientState(
    open val ingredient: IngredientModel? = IngredientModel()
) {

    data class Idle(override val ingredient: IngredientModel?) : IngredientState(ingredient)

    data class Loading(override val ingredient: IngredientModel?) : IngredientState(ingredient)

    data class Success(
        override val ingredient: IngredientModel?
    ) : IngredientState(ingredient)

    data class Error(
        val message: String,
        override val ingredient: IngredientModel? = IngredientModel()
    ) : IngredientState(ingredient)
}