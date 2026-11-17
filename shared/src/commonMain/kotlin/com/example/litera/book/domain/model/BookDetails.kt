package com.example.litera.book.domain.model
data class BookDetails(
    val id: String,
    val title: String,
    val description: String?,
    val subjects: List<String>,
    val imageUrl: String?,
    val firstPublishDate: String?
)