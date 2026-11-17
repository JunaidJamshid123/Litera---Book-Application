package com.example.litera.book.data.remote

import com.example.litera.book.data.remote.dto.BookDetailsDto
import com.example.litera.book.data.remote.dto.BookSearchResponseDto
import com.example.litera.core.data.network.safeCall
import com.example.litera.core.domain.DataError
import com.example.litera.core.domain.Result
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter

class BookApiImpl(
    private val client: HttpClient
) : BookApi {

    override suspend fun searchBooks(
        query: String,
        page: Int,
        limit: Int
    ): Result<BookSearchResponseDto, DataError.Network> = safeCall {
        client.get("$BASE_URL/search.json") {
            parameter("q", query)
            parameter("page", page)
            parameter("limit", limit)
            parameter(
                "fields",
                "key,title,author_name,author_key,cover_i,cover_edition_key," +
                        "edition_count,first_publish_year,language,ebook_access," +
                        "has_fulltext,public_scan_b," +
                        "ratings_average,ratings_count,number_of_pages_median"
            )
        }
    }

    override suspend fun getBookDetails(
        workId: String
    ): Result<BookDetailsDto, DataError.Network> = safeCall {
        client.get("$BASE_URL/works/$workId.json")
    }

    private companion object {
        const val BASE_URL = "https://openlibrary.org"
    }
}
