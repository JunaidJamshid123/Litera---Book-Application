package com.example.litera.readinglist.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.litera.readinglist.domain.model.ReadingListItem
import com.example.litera.readinglist.domain.usecase.GetReadingListUseCase
import com.example.litera.readinglist.domain.usecase.UpdateReadingStatusUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ReadingListViewModel(
    getReadingList: GetReadingListUseCase,
    private val updateReadingStatus: UpdateReadingStatusUseCase
) : ViewModel() {

    private val selectedFilter = MutableStateFlow(0)

    /** Database list + the selected filter chip, combined into one state. */
    val state: StateFlow<ReadingListState> =
        combine(getReadingList(), selectedFilter) { items, filter ->
            ReadingListState(items = items, selectedFilter = filter, isLoading = false)
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = ReadingListState()
        )

    fun onFilterChange(index: Int) {
        selectedFilter.value = index
    }

    fun onRemoveClick(item: ReadingListItem) {
        viewModelScope.launch {
            updateReadingStatus(item.id, item.title, item.imageUrl, status = null)
        }
    }
}
