package com.example.litera.book.data.remote.dto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BookDto(
    @SerialName("key") val key: String,                      // "/works/OL17930368W"
    @SerialName("title") val title: String? = null,
    @SerialName("author_key") val authorKeys: List<String>? = null,
    @SerialName("author_name") val authorNames: List<String>? = null,
    @SerialName("cover_i") val coverId: Int? = null,
    @SerialName("cover_edition_key") val coverEditionKey: String? = null,
    @SerialName("edition_count") val editionCount: Int? = null,
    @SerialName("first_publish_year") val firstPublishYear: Int? = null,
    @SerialName("language") val languages: List<String>? = null,
    @SerialName("ebook_access") val ebookAccess: String? = null,
    @SerialName("has_fulltext") val hasFulltext: Boolean? = null,
    @SerialName("public_scan_b") val publicScan: Boolean? = null,
    @SerialName("ratings_average") val ratingsAverage: Double? = null,
    @SerialName("ratings_count") val ratingsCount: Int? = null,
    @SerialName("number_of_pages_median") val numPagesMedian: Int? = null
)

