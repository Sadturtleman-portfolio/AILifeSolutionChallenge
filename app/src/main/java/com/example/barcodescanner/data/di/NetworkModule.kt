package com.example.barcodescanner.data.di

import com.example.barcodescanner.BuildConfig
import com.example.barcodescanner.data.api.BarcodeDrugApiService
import com.example.barcodescanner.data.api.BarcodeDrugRetrofit
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

    @Provides
    @Singleton
    fun provideBarcodeDrugApi(@BarcodeDrugRetrofit retrofit: Retrofit): BarcodeDrugApiService =
        retrofit.create(BarcodeDrugApiService::class.java)

}
