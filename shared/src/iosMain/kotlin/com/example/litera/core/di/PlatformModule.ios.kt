package com.example.litera.core.di

import com.example.litera.core.data.database.DatabaseDriverFactory
import com.example.litera.core.data.database.IosDatabaseDriverFactory
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.darwin.Darwin
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformModule: Module = module {
    single<HttpClientEngine> { Darwin.create() }
    single<DatabaseDriverFactory> { IosDatabaseDriverFactory() }
}
