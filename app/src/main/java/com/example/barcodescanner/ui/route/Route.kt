package com.example.barcodescanner.ui.route

import kotlinx.serialization.Serializable

sealed interface Route {
    @Serializable
    data object HomeRoute

    @Serializable
    data class RawNutritionShowRoute(
        val productCode: String
    )
}