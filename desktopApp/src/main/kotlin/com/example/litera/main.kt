package com.example.litera

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.example.litera.app.App
import com.example.litera.core.di.initKoin

fun main() {
    initKoin()
    application {
        Window(
            onCloseRequest = ::exitApplication,
            title = "Litera",
        ) {
            App()
        }
    }
}
