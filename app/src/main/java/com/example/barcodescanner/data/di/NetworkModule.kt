package com.example.barcodescanner.data.di

import com.example.barcodescanner.BuildConfig
import com.example.barcodescanner.data.api.BarcodeDrugApiService
import com.example.barcodescanner.data.api.BarcodeDrugRetrofit
import com.example.barcodescanner.data.api.FoodNutritionApiService
import com.example.barcodescanner.data.api.FoodNutritionIngredientApiService
import com.example.barcodescanner.data.api.FoodNutritionIngredientRetrofit
import com.example.barcodescanner.data.api.GeminiApiService
import com.example.barcodescanner.data.api.GeminiRetrofit
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

    @Provides
    @Singleton
    fun provideOkHttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()

    @Provides
    @Singleton
    fun provideJson(): Json = Json{
        ignoreUnknownKeys = true
        coerceInputValues = true
    }

    @BarcodeDrugRetrofit
    @Provides
    @Singleton
    fun provideBarCodeDrugRetrofit(okHttpClient: OkHttpClient, json: Json): Retrofit =
        Retrofit.Builder()
            .baseUrl(BuildConfig.DRUG_BARCODE_SAFETY_API)
            .client(okHttpClient)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()

    @FoodNutritionIngredientRetrofit
    @Provides
    @Singleton
    fun provideFoodNutritionIngredientRetrofit(okHttpClient: OkHttpClient, json: Json): Retrofit =
        Retrofit.Builder()
            .baseUrl(BuildConfig.FOOD_NUTRITION_INGREDIENT_API)
            .client(okHttpClient)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()

    @GeminiRetrofit
    @Provides
    @Singleton
    fun provideGeminiRetrofit(okHttpClient: OkHttpClient, json: Json): Retrofit =
        Retrofit.Builder()
            .baseUrl(BuildConfig.GEMINI_API)
            .client(okHttpClient)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()

    @Provides
    @Singleton
    fun provideGeminiApi(@GeminiRetrofit retrofit: Retrofit): GeminiApiService =
        retrofit.create(GeminiApiService::class.java)


    @Provides
    @Singleton
    fun provideBarcodeDrugApi(@BarcodeDrugRetrofit retrofit: Retrofit): BarcodeDrugApiService =
        retrofit.create(BarcodeDrugApiService::class.java)

    @Provides
    @Singleton
    fun provideFoodApi(@BarcodeDrugRetrofit retrofit: Retrofit): FoodNutritionApiService =
        retrofit.create(FoodNutritionApiService::class.java)


    @Provides
    @Singleton
    fun provideFoodNutritionIngredientApi(@FoodNutritionIngredientRetrofit retrofit: Retrofit): FoodNutritionIngredientApiService =
        retrofit.create(FoodNutritionIngredientApiService::class.java)
}
