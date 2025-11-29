package com.example.barcodescanner.data.di

import com.example.barcodescanner.data.repository.iface.BarcodeDrugRepository
import com.example.barcodescanner.data.repository.iface.FoodNutritionIngredientRepository
import com.example.barcodescanner.data.repository.iface.FoodNutritionRepository
import com.example.barcodescanner.data.repository.iface.GeminiRepository
import com.example.barcodescanner.data.repository.impl.BarcodeDrugImpl
import com.example.barcodescanner.data.repository.impl.FoodNutritionImpl
import com.example.barcodescanner.data.repository.impl.FoodNutritionIngredientImpl
import com.example.barcodescanner.data.repository.impl.GeminiImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindBarcodeDrugRepository(
        barcodeDrugImpl: BarcodeDrugImpl
    ) : BarcodeDrugRepository

    @Binds
    @Singleton
    abstract fun bindFoodNutritionRepository(
        foodNutritionImpl: FoodNutritionImpl
    ) : FoodNutritionRepository

    @Binds
    @Singleton
    abstract fun bindFoodNutritionIngredientRepository(
        foodNutritionIngredientImpl: FoodNutritionIngredientImpl
    ) : FoodNutritionIngredientRepository

    @Binds
    @Singleton
    abstract fun bindGeminiRepository(
        geminiImpl: GeminiImpl
    ) : GeminiRepository
}