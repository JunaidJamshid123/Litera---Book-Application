package com.example.litera.book.data.mapper
import com.example.litera.book.data.remote.dto.BookDetailsDto
import com.example.litera.book.data.remote.dto.BookDto
import com.example.litera.book.domain.model.Book
import com.example.litera.book.data.remote.dto.BookSearchResponseDto
import com.example.litera.book.domain.model.BookDetails
import com.example.litera.book.domain.model.BookSearchResult

private const val COVERS_BASE_URL = "https://covers.openlibrary.org/b"
fun BookDto.toBook(): Book {
    val imageUrl = when {
        // ?default=false -> 404 instead of a blank 1x1 image when there is no cover
        coverId != null -> "$COVERS_BASE_URL/id/$coverId-L.jpg?default=false"
        coverEditionKey != null -> "$COVERS_BASE_URL/olid/$coverEditionKey-L.jpg?default=false"
        else -> ""
    }

    return Book(
        id = key.removePrefix("/works/"),
        title = title.orEmpty(),
        imageUrl = imageUrl,
        authors = authorNames.orEmpty(),
        description = null,
        languages = languages.orEmpty(),
        firstPublishYear = firstPublishYear?.toString(),
        averageRating = ratingsAverage,       // changed
        ratingCount = ratingsCount,           // changed
        numPages = numPagesMedian,            // changed
        numEditions = editionCount ?: 0
    )
}

fun BookDetailsDto.toBook(base: Book): Book {
    val detailsImageUrl = covers
        ?.firstOrNull { it > 0 }
        ?.let { "$COVERS_BASE_URL/id/$it-L.jpg" }

    return base.copy(
        title = title ?: base.title,
        imageUrl = base.imageUrl.ifEmpty { detailsImageUrl.orEmpty() },
        description = description?.takeIf { it.isNotBlank() } ?: base.description,
        firstPublishYear = base.firstPublishYear ?: firstPublishDate
    )
}


fun BookSearchResponseDto.toBookSearchResult(): BookSearchResult =
    BookSearchResult(
        books = results.map { it.toBook() },
        totalCount = numFound,
        offset = start
    )

fun BookDetailsDto.toBookDetails(): BookDetails =
    BookDetails(
        id = key.removePrefix("/works/"),
        title = title.orEmpty(),
        description = description?.takeIf { it.isNotBlank() },
        subjects = subjects.orEmpty(),
        imageUrl = covers
            ?.firstOrNull { it > 0 }
            ?.let { "$COVERS_BASE_URL/id/$it-L.jpg" },
        firstPublishDate = firstPublishDate
    )