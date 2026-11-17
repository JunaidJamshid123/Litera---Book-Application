package com.example.litera.book.data.repository

import com.example.litera.book.data.mapper.toBookDetails
import com.example.litera.book.data.mapper.toBookSearchResult
import com.example.litera.book.data.remote.BookApi
import com.example.litera.book.domain.model.BookDetails
import com.example.litera.book.domain.model.BookSearchResult
import com.example.litera.book.domain.repository.BookRepository
import com.example.litera.core.domain.DataError
import com.example.litera.core.domain.Result
import com.example.litera.core.domain.map

class BookRepositoryImpl(
    private val bookApi: BookApi
) : BookRepository {

    override suspend fun searchBooks(
        query: String,
        page: Int,
        limit: Int
    ): Result<BookSearchResult, DataError.Network> =
        bookApi.searchBooks(query, page, limit).map { it.toBookSearchResult() }

    override suspend fun getBookDetails(
        workId: String
    ): Result<BookDetails, DataError.Network> =
        bookApi.getBookDetails(workId).map { it.toBookDetails() }
}
