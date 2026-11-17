package com.example.litera.book.presentation.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.litera.book.domain.usecase.GetBookDetailsUseCase
import com.example.litera.core.domain.onError
import com.example.litera.core.domain.onSuccess
import com.example.litera.core.presentation.toUiText
import com.example.litera.readinglist.domain.model.ReadingStatus
import com.example.litera.readinglist.domain.usecase.GetReadingListUseCase
import com.example.litera.readinglist.domain.usecase.UpdateReadingStatusUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/** [workId] comes from the navigation route (passed in by Koin via parametersOf). */
class BookDetailsViewModel(
    private val workId: String,
    private val getBookDetails: GetBookDetailsUseCase,
    getReadingList: GetReadingListUseCase,
    private val updateReadingStatus: UpdateReadingStatusUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(BookDetailsState())
    val state = _state.asStateFlow()

    init {
        loadDetails()

        // Keep the status buttons in sync with the database
        getReadingList()
            .map { list -> list.firstOrNull { it.id == workId }?.status }
            .distinctUntilChanged()
            .onEach { status -> _state.update { it.copy(readingStatus = status) } }
            .launchIn(viewModelScope)
    }

    fun onRetryClick() = loadDetails()

    fun onReadingStatusClick(status: ReadingStatus) {
        val book = _state.value.book ?: return
        // Tapping the current status again removes the book from the list
        val newStatus = if (_state.value.readingStatus == status) null else status

        viewModelScope.launch {
            updateReadingStatus(
                bookId = workId,
                title = book.title,
                imageUrl = book.imageUrl,
                status = newStatus
            )
        }
    }

    private fun loadDetails() {
        _state.update { it.copy(isLoading = true, error = null) }

        viewModelScope.launch {
            getBookDetails(workId)
                .onSuccess { details ->
                    _state.update { it.copy(isLoading = false, book = details, error = null) }
                }
                .onError { error ->
                    _state.update { it.copy(isLoading = false, error = error.toUiText()) }
                }
        }
    }
}
