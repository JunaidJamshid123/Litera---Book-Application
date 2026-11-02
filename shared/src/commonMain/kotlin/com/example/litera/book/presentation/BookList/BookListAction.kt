package com.example.litera.book.presentation.BookList

import com.example.litera.book.domin.Book

sealed interface BookListAction {
    data class onSearchQueryChange(val query: String) : BookListAction
    data class onTabChange(val tab: Int) : BookListAction
    data class onBookClick(val book: Book) : BookListAction
    data class onFavoriteClick(val book: Book) : BookListAction
}