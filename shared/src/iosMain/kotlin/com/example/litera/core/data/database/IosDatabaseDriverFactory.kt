package com.example.litera.core.data.database

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import com.example.litera.database.LiteraDatabase

/** iOS: database file lives in the app's sandboxed Library directory. */
class IosDatabaseDriverFactory : DatabaseDriverFactory {

    override fun create(): SqlDriver =
        NativeSqliteDriver(
            schema = LiteraDatabase.Schema,
            name = DATABASE_NAME
        )
}
