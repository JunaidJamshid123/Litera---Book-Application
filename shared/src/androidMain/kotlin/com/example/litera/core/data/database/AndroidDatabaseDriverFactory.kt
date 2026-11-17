package com.example.litera.core.data.database

import android.content.Context
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.example.litera.database.LiteraDatabase

/** Android: database file lives in the app's private storage (/data/data/<app>/databases). */
class AndroidDatabaseDriverFactory(
    private val context: Context
) : DatabaseDriverFactory {

    override fun create(): SqlDriver =
        AndroidSqliteDriver(
            schema = LiteraDatabase.Schema,
            context = context,
            name = DATABASE_NAME
        )
}
