package com.example.litera

import androidx.compose.ui.window.ComposeUIViewController
import com.example.litera.app.App
import com.example.litera.core.di.initKoin

fun MainViewController() = ComposeUIViewController(
    configure = { initKoin() }
) { App() }
