package com.example.litera.book.presentation.search

import com.example.litera.book.domain.model.Book
import com.example.litera.core.presentation.UiText

data class SearchState(
    val searchedText: String = "",
    val searchedBooks: List<Book> = emptyList(),
    val favoriteBooks: List<Book> = emptyList(),
    val isLoading: Boolean = false,
    val error: UiText? = null,
    val selectedTab: Int = 0,
)
