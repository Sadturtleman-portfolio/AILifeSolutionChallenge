package com.example.barcodescanner

import android.os.Bundle
import android.speech.tts.TextToSpeech
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.CompositionLocalProvider
import com.example.barcodescanner.data.model.LocalTTS
import com.example.barcodescanner.data.model.initTTS
import com.example.barcodescanner.ui.route.NavGraph
import com.example.barcodescanner.ui.theme.BarcodeScannerTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private var tts: TextToSpeech? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        tts = initTTS(this@MainActivity)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CompositionLocalProvider(LocalTTS provides tts) {
                BarcodeScannerTheme {
                    NavGraph()
                }
            }
        }
    }

    override fun onDestroy() {
        if (tts != null) {
            tts?.stop()
            tts?.shutdown()
        }
        super.onDestroy()
    }
}
