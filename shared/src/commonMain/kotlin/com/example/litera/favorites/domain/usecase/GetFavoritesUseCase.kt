package com.example.litera.favorites.domain.usecase

import com.example.litera.book.domain.model.Book
import com.example.litera.favorites.domain.repository.FavoritesRepository
import kotlinx.coroutines.flow.Flow

class GetFavoritesUseCase(
    private val repository: FavoritesRepository
) {
    operator fun invoke(): Flow<List<Book>> = repository.getFavorites()
}
