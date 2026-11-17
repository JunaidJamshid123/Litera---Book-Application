package com.example.litera.readinglist.domain.usecase

import com.example.litera.readinglist.domain.model.ReadingListItem
import com.example.litera.readinglist.domain.repository.ReadingListRepository
import kotlinx.coroutines.flow.Flow

class GetReadingListUseCase(
    private val repository: ReadingListRepository
) {
    operator fun invoke(): Flow<List<ReadingListItem>> = repository.getReadingList()
}
