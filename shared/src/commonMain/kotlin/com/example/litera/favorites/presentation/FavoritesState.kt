package com.example.litera.favorites.presentation

import com.example.litera.book.domain.model.Book

data class FavoritesState(
    val books: List<Book> = emptyList(),
    val isLoading: Boolean = true
)
