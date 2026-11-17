package com.example.litera.favorites.data.repository

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.example.litera.book.domain.model.Book
import com.example.litera.core.data.database.safeDbCall
import com.example.litera.core.domain.DataError
import com.example.litera.core.domain.EmptyResult
import com.example.litera.database.FavoriteBookQueries
import com.example.litera.favorites.data.mapper.toBook
import com.example.litera.favorites.domain.repository.FavoritesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class FavoritesRepositoryImpl(
    private val queries: FavoriteBookQueries
) : FavoritesRepository {

    override fun getFavorites(): Flow<List<Book>> =
        queries.selectAll()
            .asFlow()                   // re-runs the query whenever the table changes
            .mapToList(Dispatchers.IO)  // executes it off the main thread
            .map { rows -> rows.map { it.toBook() } }

    override suspend fun addFavorite(book: Book): EmptyResult<DataError.Local> = safeDbCall {
        queries.insert(
            id = book.id,
            title = book.title,
            image_url = book.imageUrl,
            authors = book.authors,
            description = book.description,
            languages = book.languages,
            first_publish_year = book.firstPublishYear,
            average_rating = book.averageRating,
            rating_count = book.ratingCount?.toLong(),
            num_pages = book.numPages?.toLong(),
            num_editions = book.numEditions.toLong()
        )
    }

    override suspend fun removeFavorite(bookId: String): EmptyResult<DataError.Local> = safeDbCall {
        queries.deleteById(bookId)
    }
}
