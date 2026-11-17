package com.example.litera.core.data.database

import app.cash.sqldelight.EnumColumnAdapter
import com.example.litera.database.FavoriteBook
import com.example.litera.database.LiteraDatabase
import com.example.litera.database.ReadingListBook

/** Builds the single LiteraDatabase instance, plugging in the column adapters. */
object DatabaseFactory {

    fun create(driverFactory: DatabaseDriverFactory): LiteraDatabase =
        LiteraDatabase(
            driver = driverFactory.create(),
            favoriteBookAdapter = FavoriteBook.Adapter(
                authorsAdapter = StringListAdapter,
                languagesAdapter = StringListAdapter
            ),
            readingListBookAdapter = ReadingListBook.Adapter(
                // Stores the enum by name: "WANT_TO_READ", "READING", "FINISHED"
                statusAdapter = EnumColumnAdapter()
            )
        )
}
