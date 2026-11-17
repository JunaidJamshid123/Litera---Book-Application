package com.example.litera.book.domain.usecase

import com.example.litera.book.domain.model.BookSearchResult
import com.example.litera.book.domain.repository.BookRepository
import com.example.litera.core.domain.DataError
import com.example.litera.core.domain.Result

class SearchBooksUseCase(
    private val repository: BookRepository
) {
    suspend operator fun invoke(
        query: String,
        page: Int = 1,
        limit: Int = 20
    ): Result<BookSearchResult, DataError.Network> {
        val trimmed = query.trim()
        if (trimmed.isBlank()) {
            return Result.Success(BookSearchResult(emptyList(), 0, 0))
        }
        return repository.searchBooks(trimmed, page, limit)
    }
}
