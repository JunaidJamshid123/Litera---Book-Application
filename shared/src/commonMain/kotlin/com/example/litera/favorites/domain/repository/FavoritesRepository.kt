package com.example.litera.favorites.domain.repository

import com.example.litera.book.domain.model.Book
import com.example.litera.core.domain.DataError
import com.example.litera.core.domain.EmptyResult
import kotlinx.coroutines.flow.Flow

interface FavoritesRepository {

    /** Emits the current favorites, and again every time they change. */
    fun getFavorites(): Flow<List<Book>>

    suspend fun addFavorite(book: Book): EmptyResult<DataError.Local>

    suspend fun removeFavorite(bookId: String): EmptyResult<DataError.Local>
}
