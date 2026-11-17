package com.example.litera.favorites.domain.usecase

import com.example.litera.book.domain.model.Book
import com.example.litera.core.domain.DataError
import com.example.litera.core.domain.EmptyResult
import com.example.litera.favorites.domain.repository.FavoritesRepository

class AddFavoriteUseCase(
    private val repository: FavoritesRepository
) {
    suspend operator fun invoke(book: Book): EmptyResult<DataError.Local> =
        repository.addFavorite(book)
}
