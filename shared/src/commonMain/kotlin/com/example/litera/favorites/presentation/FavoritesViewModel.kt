package com.example.litera.favorites.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.litera.book.domain.model.Book
import com.example.litera.favorites.domain.usecase.GetFavoritesUseCase
import com.example.litera.favorites.domain.usecase.RemoveFavoriteUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class FavoritesViewModel(
    getFavorites: GetFavoritesUseCase,
    private val removeFavorite: RemoveFavoriteUseCase
) : ViewModel() {

    /**
     * The state comes straight from the database Flow: when a favorite is
     * added/removed anywhere in the app, this screen updates automatically.
     */
    val state: StateFlow<FavoritesState> = getFavorites()
        .map { books -> FavoritesState(books = books, isLoading = false) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = FavoritesState()
        )

    fun onRemoveClick(book: Book) {
        viewModelScope.launch { removeFavorite(book.id) }
    }
}
