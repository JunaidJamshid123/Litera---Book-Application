package com.example.litera.readinglist.domain.model

/** A book on the reading list, as the rest of the app sees it. */
data class ReadingListItem(
    val id: String,
    val title: String,
    val imageUrl: String?,
    val status: ReadingStatus
)
