package com.example.litera.core.di

import com.example.litera.core.data.database.AndroidDatabaseDriverFactory
import com.example.litera.core.data.database.DatabaseDriverFactory
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.okhttp.OkHttp
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformModule: Module = module {
    single<HttpClientEngine> { OkHttp.create() }
    // androidContext() is the Context passed in LiteraApp: initKoin { androidContext(this) }
    single<DatabaseDriverFactory> { AndroidDatabaseDriverFactory(androidContext()) }
}
