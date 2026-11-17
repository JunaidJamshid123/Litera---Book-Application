package com.example.litera.readinglist.data.repository

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.example.litera.core.data.database.safeDbCall
import com.example.litera.core.domain.DataError
import com.example.litera.core.domain.EmptyResult
import com.example.litera.database.ReadingListBook
import com.example.litera.database.ReadingListBookQueries
import com.example.litera.readinglist.domain.model.ReadingListItem
import com.example.litera.readinglist.domain.repository.ReadingListRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ReadingListRepositoryImpl(
    private val queries: ReadingListBookQueries
) : ReadingListRepository {

    override fun getReadingList(): Flow<List<ReadingListItem>> =
        queries.selectAll()
            .asFlow()
            .mapToList(Dispatchers.IO)
            .map { rows -> rows.map { it.toReadingListItem() } }

    override suspend fun upsert(item: ReadingListItem): EmptyResult<DataError.Local> = safeDbCall {
        queries.upsert(
            id = item.id,
            title = item.title,
            image_url = item.imageUrl,
            status = item.status
        )
    }

    override suspend fun remove(bookId: String): EmptyResult<DataError.Local> = safeDbCall {
        queries.deleteById(bookId)
    }

    private fun ReadingListBook.toReadingListItem() = ReadingListItem(
        id = id,
        title = title,
        imageUrl = image_url,
        status = status
    )
}
