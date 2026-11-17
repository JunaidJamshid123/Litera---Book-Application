package com.example.litera.readinglist.domain.repository

import com.example.litera.core.domain.DataError
import com.example.litera.core.domain.EmptyResult
import com.example.litera.readinglist.domain.model.ReadingListItem
import kotlinx.coroutines.flow.Flow

interface ReadingListRepository {

    /** Emits the reading list, and again every time it changes. */
    fun getReadingList(): Flow<List<ReadingListItem>>

    /** Adds the book, or updates its status if it is already on the list. */
    suspend fun upsert(item: ReadingListItem): EmptyResult<DataError.Local>

    suspend fun remove(bookId: String): EmptyResult<DataError.Local>
}
