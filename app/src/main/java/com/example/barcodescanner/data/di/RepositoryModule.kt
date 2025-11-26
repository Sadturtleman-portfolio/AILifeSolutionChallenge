package com.example.barcodescanner.data.di

import com.example.barcodescanner.data.repository.iface.BarcodeDrugRepository
import com.example.barcodescanner.data.repository.impl.BarcodeDrugImpl
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
}