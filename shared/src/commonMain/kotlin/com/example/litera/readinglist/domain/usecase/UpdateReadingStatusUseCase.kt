package com.example.litera.readinglist.domain.usecase

import com.example.litera.core.domain.DataError
import com.example.litera.core.domain.EmptyResult
import com.example.litera.readinglist.domain.model.ReadingListItem
import com.example.litera.readinglist.domain.model.ReadingStatus
import com.example.litera.readinglist.domain.repository.ReadingListRepository

/**
 * Sets a book's reading status. Passing `status = null` removes the book
 * from the reading list.
 */
class UpdateReadingStatusUseCase(
    private val repository: ReadingListRepository
) {
    suspend operator fun invoke(
        bookId: String,
        title: String,
        imageUrl: String?,
        status: ReadingStatus?
    ): EmptyResult<DataError.Local> =
        if (status == null) {
            repository.remove(bookId)
        } else {
            repository.upsert(ReadingListItem(bookId, title, imageUrl, status))
        }
}
