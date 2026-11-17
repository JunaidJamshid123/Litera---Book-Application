package com.example.litera.book.presentation.details

import com.example.litera.book.domain.model.BookDetails
import com.example.litera.core.presentation.UiText
import com.example.litera.readinglist.domain.model.ReadingStatus

data class BookDetailsState(
    val isLoading: Boolean = true,
    val book: BookDetails? = null,
    val error: UiText? = null,
    /** Status saved in the local database, or null if the book isn't on the reading list. */
    val readingStatus: ReadingStatus? = null
)
