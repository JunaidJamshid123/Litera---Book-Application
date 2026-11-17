package com.example.litera.core.data.database

import app.cash.sqldelight.db.SqlDriver

/**
 * Creates the platform's SQLite driver. Each platform provides its own
 * implementation (androidMain / iosMain / jvmMain), registered in platformModule.
 */
interface DatabaseDriverFactory {
    fun create(): SqlDriver
}

const val DATABASE_NAME = "litera.db"
