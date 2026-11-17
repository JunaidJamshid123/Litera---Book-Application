package com.example.litera.book.data.remote

import com.example.litera.book.data.remote.dto.BookDetailsDto
import com.example.litera.book.data.remote.dto.BookSearchResponseDto
import com.example.litera.core.domain.DataError
import com.example.litera.core.domain.Result

interface BookApi {
    suspend fun searchBooks(
        query: String,
        page: Int = 1,
        limit: Int = 20
    ): Result<BookSearchResponseDto, DataError.Network>

    suspend fun getBookDetails(workId: String): Result<BookDetailsDto, DataError.Network>
}
