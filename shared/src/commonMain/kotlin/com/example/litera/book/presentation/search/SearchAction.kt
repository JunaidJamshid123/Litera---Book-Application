package com.example.litera.book.presentation.search

import com.example.litera.book.domain.model.Book

sealed interface SearchAction {
    data class onSearchQueryChange(val query: String) : SearchAction
    data class onTabChange(val tab: Int) : SearchAction
    data class onBookClick(val book: Book) : SearchAction
    data class onFavoriteClick(val book: Book) : SearchAction
    data object onRetryClick : SearchAction
}
