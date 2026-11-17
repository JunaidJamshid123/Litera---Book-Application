package com.example.litera.core.data.database

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import com.example.litera.database.LiteraDatabase
import java.io.File
import java.util.Properties

/** Desktop: database file lives in ~/.litera/litera.db */
class DesktopDatabaseDriverFactory : DatabaseDriverFactory {

    override fun create(): SqlDriver {
        val folder = File(System.getProperty("user.home"), ".litera").apply { mkdirs() }
        val dbFile = File(folder, DATABASE_NAME)

        // Passing the schema makes the driver create the tables on first run
        // (and migrate them when the schema version changes).
        return JdbcSqliteDriver(
            url = "jdbc:sqlite:${dbFile.absolutePath}",
            properties = Properties(),
            schema = LiteraDatabase.Schema
        )
    }
}
