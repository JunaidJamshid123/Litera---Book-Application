package com.example.litera.book.domain.usecase

import com.example.litera.book.domain.model.BookSearchResult
import com.example.litera.book.domain.repository.BookRepository
import com.example.litera.core.domain.DataError
import com.example.litera.core.domain.Result

class GetBooksUseCase(
    private val repository: BookRepository
) {
    suspend operator fun invoke(
        page: Int = 1,
        limit: Int = 20
    ): Result<BookSearchResult, DataError.Network> =
        repository.searchBooks(DEFAULT_QUERY, page, limit)

    private companion object {
        const val DEFAULT_QUERY = "subject:fiction"
    }
}
