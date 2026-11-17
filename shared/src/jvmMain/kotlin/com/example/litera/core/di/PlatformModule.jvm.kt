package com.example.litera.core.di

import com.example.litera.core.data.database.DatabaseDriverFactory
import com.example.litera.core.data.database.DesktopDatabaseDriverFactory
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.okhttp.OkHttp
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformModule: Module = module {
    single<HttpClientEngine> { OkHttp.create() }
    single<DatabaseDriverFactory> { DesktopDatabaseDriverFactory() }
}
