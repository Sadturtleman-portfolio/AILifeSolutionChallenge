package com.example.barcodescanner.data.api

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class BarcodeDrugRetrofit

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class FoodNutritionIngredientRetrofit

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class GeminiRetrofit