package com.example.barcodescanner.data.model

import android.content.Context
import android.speech.tts.TextToSpeech
import android.util.Log
import androidx.compose.runtime.compositionLocalOf
import java.util.Locale

fun initTTS(context: Context): TextToSpeech {
    var tts: TextToSpeech? = null
    tts = TextToSpeech(context) {
        if (it == TextToSpeech.SUCCESS) {
            val result = tts!!.setLanguage(Locale.KOREAN)
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TextToSpeech", "해당 언어는 지원되지 않습니다.")
                return@TextToSpeech
            }
        }
    }
    return tts
}

fun TextToSpeech?.readText(text: String) {
    this?.speak(text, TextToSpeech.QUEUE_FLUSH, null, "")
    this?.playSilentUtterance(500, TextToSpeech.QUEUE_ADD, null)
}

val LocalTTS = compositionLocalOf<TextToSpeech?> { null }