package com.example.litera.book.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BookSearchResponseDto(
    @SerialName("numFound") val numFound: Int = 0,
    @SerialName("start") val start: Int = 0,
    @SerialName("numFoundExact") val numFoundExact: Boolean = true,
    @SerialName("docs") val results: List<BookDto> = emptyList()
)
