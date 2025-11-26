package com.example.barcodescanner.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.barcodescanner.data.repository.iface.BarcodeDrugRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BarcodeViewModel @Inject constructor(
    private val barcodeDrugRepository: BarcodeDrugRepository
): ViewModel(){

}