package com.example.litera.book.domain.repository

import com.example.litera.book.domain.model.BookDetails
import com.example.litera.book.domain.model.BookSearchResult
import com.example.litera.core.domain.DataError
import com.example.litera.core.domain.Result

interface BookRepository {

    suspend fun searchBooks(
        query: String,
        page: Int = 1,
        limit: Int = 20
    ): Result<BookSearchResult, DataError.Network>

    suspend fun getBookDetails(workId: String): Result<BookDetails, DataError.Network>
}
