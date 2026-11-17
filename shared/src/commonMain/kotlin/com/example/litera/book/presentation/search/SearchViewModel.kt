package com.example.litera.book.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.litera.book.domain.model.Book
import com.example.litera.book.domain.usecase.GetBooksUseCase
import com.example.litera.favorites.domain.usecase.AddFavoriteUseCase
import com.example.litera.favorites.domain.usecase.GetFavoritesUseCase
import com.example.litera.favorites.domain.usecase.RemoveFavoriteUseCase
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import com.example.litera.book.domain.usecase.SearchBooksUseCase
import com.example.litera.core.domain.onError
import com.example.litera.core.domain.onSuccess
import com.example.litera.core.presentation.toUiText
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SearchViewModel(
    private val searchBooks: SearchBooksUseCase,
    private val getBooks: GetBooksUseCase,
    getFavorites: GetFavoritesUseCase,
    private val addFavorite: AddFavoriteUseCase,
    private val removeFavorite: RemoveFavoriteUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(SearchState())
    val state = _state.asStateFlow()

    private var searchJob: Job? = null

    init {
        // Show "All Books" as soon as the screen opens
        loadBooks(debounce = false)

        // Keep the ♥ icons in sync with the local database
        getFavorites()
            .onEach { favorites -> _state.update { it.copy(favoriteBooks = favorites) } }
            .launchIn(viewModelScope)
    }

    fun onAction(action: SearchAction) {
        when (action) {
            is SearchAction.onSearchQueryChange -> {
                _state.update { it.copy(searchedText = action.query) }
                loadBooks(debounce = true)
            }
            is SearchAction.onTabChange -> {
                if (action.tab == _state.value.selectedTab) return
                _state.update { it.copy(selectedTab = action.tab) }
                loadBooks(debounce = false)
            }
            is SearchAction.onRetryClick -> loadBooks(debounce = false)
            is SearchAction.onBookClick -> Unit      // handled by navigation (AppNavHost)
            is SearchAction.onFavoriteClick -> toggleFavorite(action.book)
        }
    }

    private fun toggleFavorite(book: Book) {
        val isFavorite = _state.value.favoriteBooks.any { it.id == book.id }
        viewModelScope.launch {
            if (isFavorite) removeFavorite(book.id) else addFavorite(book)
            // No manual state update needed: the database Flow above emits the new list
        }
    }

    /** Loads books for the current search text + selected category. */
    private fun loadBooks(debounce: Boolean) {
        // Cancel the previous request so only the latest one wins
        searchJob?.cancel()

        val current = _state.value
        val category = BookCategory.entries.getOrElse(current.selectedTab) { BookCategory.ALL }
        val query = listOfNotNull(
            current.searchedText.trim().ifBlank { null },
            category.subject?.let { "subject:$it" }
        ).joinToString(" ")

        _state.update { it.copy(isLoading = true, error = null) }

        searchJob = viewModelScope.launch {
            if (debounce) delay(SEARCH_DEBOUNCE_MS)

            val result = if (query.isBlank()) getBooks() else searchBooks(query)

            result
                .onSuccess { books ->
                    _state.update {
                        it.copy(searchedBooks = books.books, isLoading = false, error = null)
                    }
                }
                .onError { error ->
                    _state.update {
                        it.copy(searchedBooks = emptyList(), isLoading = false, error = error.toUiText())
                    }
                }
        }
    }

    private companion object {
        const val SEARCH_DEBOUNCE_MS = 500L
    }
}