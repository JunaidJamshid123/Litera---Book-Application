package com.example.litera.core.data.database

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import com.example.litera.book.domain.model.Book
import com.example.litera.core.domain.Result
import com.example.litera.database.LiteraDatabase
import com.example.litera.favorites.data.repository.FavoritesRepositoryImpl
import com.example.litera.readinglist.data.repository.ReadingListRepositoryImpl
import com.example.litera.readinglist.domain.model.ReadingListItem
import com.example.litera.readinglist.domain.model.ReadingStatus
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import java.util.Properties
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs
import kotlin.test.assertTrue

/** Runs the real repositories against a fresh in-memory SQLite database. */
class LocalDatabaseTest {

    private val database = DatabaseFactory.create(object : DatabaseDriverFactory {
        override fun create(): SqlDriver =
            JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY, Properties(), LiteraDatabase.Schema)
    })

    private val book = Book(
        id = "OL17930368W",
        title = "Atomic Habits",
        imageUrl = "https://covers.openlibrary.org/b/id/12539702-L.jpg",
        authors = listOf("James Clear", "Co Author"),
        description = null,
        languages = listOf("eng"),
        firstPublishYear = "2018",
        averageRating = 4.1,
        ratingCount = 120,
        numPages = 320,
        numEditions = 25
    )

    @Test
    fun favorites_add_read_remove() = runBlocking {
        val repo = FavoritesRepositoryImpl(database.favoriteBookQueries)

        assertIs<Result.Success<Unit>>(repo.addFavorite(book))
        // Lists survive the JSON column adapter round-trip unchanged
        assertEquals(listOf(book), repo.getFavorites().first())

        // Adding the same book twice must not create a duplicate (INSERT OR REPLACE)
        repo.addFavorite(book)
        assertEquals(1, repo.getFavorites().first().size)

        repo.removeFavorite(book.id)
        assertTrue(repo.getFavorites().first().isEmpty())
    }

    @Test
    fun readingList_upsert_changes_status_and_remove() = runBlocking {
        val repo = ReadingListRepositoryImpl(database.readingListBookQueries)
        val item = ReadingListItem(book.id, book.title, book.imageUrl, ReadingStatus.WANT_TO_READ)

        repo.upsert(item)
        repo.upsert(item.copy(status = ReadingStatus.READING))

        val list = repo.getReadingList().first()
        assertEquals(1, list.size)
        assertEquals(ReadingStatus.READING, list.single().status)

        repo.remove(book.id)
        assertTrue(repo.getReadingList().first().isEmpty())
    }
}
