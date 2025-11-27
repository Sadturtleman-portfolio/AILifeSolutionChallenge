package com.example.barcodescanner.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.barcodescanner.ui.state.BarcodeDrugState
import com.example.barcodescanner.ui.viewmodel.BarcodeViewModel
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.codescanner.GmsBarcodeScannerOptions
import com.google.mlkit.vision.codescanner.GmsBarcodeScanning

@Composable
fun HomeScreen(
    onScanClick: (String) -> Unit,
    modifier: Modifier = Modifier,
    barcodeDrugViewModel: BarcodeViewModel = hiltViewModel()
){
    val context = LocalContext.current
    val state by barcodeDrugViewModel.barcodeDrugState.collectAsState()

    val scannerOptions = GmsBarcodeScannerOptions.Builder()
        .setBarcodeFormats(Barcode.FORMAT_EAN_13)
        .enableAutoZoom()
        .build()

    val scanner = GmsBarcodeScanning.getClient(context, scannerOptions)

    when (state){
        is BarcodeDrugState.Idle -> {
            IdleStateHomeScreen(modifier = modifier) {
                scanner.startScan()
                    .addOnSuccessListener {barcode ->
                        val raw = barcode.rawValue ?: ""
                        barcodeDrugViewModel.scanBarcode(raw)
                    }
                    .addOnFailureListener {exception ->
                        barcodeDrugViewModel.scanFailed(exception.message)
                    }
            }
        }
        is BarcodeDrugState.Loading -> {
            CircularProgressIndicator()
        }
        is BarcodeDrugState.Success -> {
            onScanClick((state as BarcodeDrugState.Success).data.reportNumber ?: "")
        }
        is BarcodeDrugState.Error -> {
            Column {
                Text(
                    (state as BarcodeDrugState.Error).message
                )
                Text(
                    (state as BarcodeDrugState.Error).barcode
                )
            }
        }
    }
}

@Composable
private fun IdleStateHomeScreen(modifier: Modifier = Modifier, onScanClick: () -> Unit){
    Column(
        modifier = modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = onScanClick
        ) {
            Text("스캔")
        }
    }
}