package com.example.litera.book.presentation.BookList

import com.example.litera.book.domin.Book
import com.example.litera.core.presentation.UiText

data class BookListState(
    val searchedText: String = "koltin",
    val searchedBooks: List<Book> = emptyList(),
    val favoriteBooks: List<Book> = emptyList(),
    val isLoading: Boolean = false,
    val error: UiText? = null,
    val selectedTab: Int = 0,
    )