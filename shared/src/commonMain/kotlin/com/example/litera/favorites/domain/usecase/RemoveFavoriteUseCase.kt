package com.example.litera.favorites.domain.usecase

import com.example.litera.core.domain.DataError
import com.example.litera.core.domain.EmptyResult
import com.example.litera.favorites.domain.repository.FavoritesRepository

class RemoveFavoriteUseCase(
    private val repository: FavoritesRepository
) {
    suspend operator fun invoke(bookId: String): EmptyResult<DataError.Local> =
        repository.removeFavorite(bookId)
}
