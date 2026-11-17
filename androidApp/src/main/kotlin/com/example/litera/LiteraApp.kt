package com.example.litera

import android.app.Application
import com.example.litera.core.di.initKoin
import org.koin.android.ext.koin.androidContext

class LiteraApp : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin { androidContext(this@LiteraApp) }
    }
}
