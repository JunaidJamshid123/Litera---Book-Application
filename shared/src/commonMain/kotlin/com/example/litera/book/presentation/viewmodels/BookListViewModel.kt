package com.example.litera.book.presentation.viewmodels

import androidx.lifecycle.ViewModel
import com.example.litera.book.presentation.BookList.BookListAction
import com.example.litera.book.presentation.BookList.BookListState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class BookListViewModel: ViewModel() {
    private val _state = MutableStateFlow(BookListState())
    val state = _state.asStateFlow()
    fun onAction(action: BookListAction ){
        when(action){
            is BookListAction.onTabChange -> {
                _state.value = _state.value.copy(selectedTab = action.tab)
            }
            is BookListAction.onSearchQueryChange -> {
                _state.value = _state.value.copy(searchedText = action.query)
            }
//            is BookListAction.onBookClick -> {
//                _state.value = _state.value.copy(selectedBook = action.book)
//            }
            else -> {}
        }
        }
    }
}