package com.example.litera.book.domain.usecase

import com.example.litera.book.domain.model.BookDetails
import com.example.litera.book.domain.repository.BookRepository
import com.example.litera.core.domain.DataError
import com.example.litera.core.domain.Result

class GetBookDetailsUseCase(
    private val repository: BookRepository
) {
    suspend operator fun invoke(workId: String): Result<BookDetails, DataError.Network> =
        repository.getBookDetails(workId.removePrefix("/works/"))
}
