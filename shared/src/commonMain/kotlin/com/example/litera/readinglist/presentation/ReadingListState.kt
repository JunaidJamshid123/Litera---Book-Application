package com.example.litera.readinglist.presentation

import com.example.litera.readinglist.domain.model.ReadingListItem
import com.example.litera.readinglist.domain.model.ReadingStatus

/** Filter chips: index 0 = "All", then one per ReadingStatus. */
val readingListFilters: List<String> = listOf("All") + ReadingStatus.entries.map { it.label }

data class ReadingListState(
    val items: List<ReadingListItem> = emptyList(),
    val selectedFilter: Int = 0,
    val isLoading: Boolean = true
) {
    val visibleItems: List<ReadingListItem>
        get() = if (selectedFilter == 0) {
            items
        } else {
            val status = ReadingStatus.entries[selectedFilter - 1]
            items.filter { it.status == status }
        }
}
