package com.example.litera.book.domain.model
data class BookSearchResult(
    val books: List<Book>,
    val totalCount: Int,
    val offset: Int
)