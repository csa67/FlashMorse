package com.example.myapplication

import android.content.Context
import android.hardware.camera2.CameraManager
import androidx.compose.runtime.Composable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

fun flashMorseCode(text: String, context: Context) {
    val cameraManager = context.getSystemService(Context.CAMERA_SERVICE) as CameraManager
    val cameraId = cameraManager.cameraIdList[0]
    val morseSequence = text.uppercase().mapNotNull { morseCodeMap[it] }.joinToString(" ")

    CoroutineScope(Dispatchers.Main).launch {
        for (symbol in morseSequence) {
            when (symbol) {
                '.' -> flashLight(200, cameraId, cameraManager) // Short flash
                '-' -> flashLight(600, cameraId, cameraManager) // Long flash
                ' ' -> delay(600) // Space between letters
            }
            delay(200) // Small gap after each symbol
        }
    }
}

private suspend fun flashLight(duration: Long, cameraId: String, cameraManager: CameraManager) {
    cameraManager.setTorchMode(cameraId, true)
    delay(duration)
    cameraManager.setTorchMode(cameraId, false)
}