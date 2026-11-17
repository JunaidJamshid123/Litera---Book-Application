package com.example.litera.book.presentation.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.litera.book.domain.model.Book
import com.example.litera.book.presentation.home.components.BookListItem
import com.example.litera.book.presentation.search.components.BookSearchComponent
import com.example.litera.book.presentation.search.components.CategoryChipRow
import com.example.litera.core.presentation.components.EmptyView
import com.example.litera.core.presentation.components.ErrorView
import com.example.litera.core.presentation.components.LoadingIndicator
import org.koin.compose.viewmodel.koinViewModel

/** Stateful entry point: gets the ViewModel from Koin and observes its state. */
@Composable
fun SearchScreen(
    viewModel: SearchViewModel = koinViewModel(),
    onBookClick: (Book) -> Unit = {}
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    SearchScreenContent(
        state = state,
        onAction = { action ->
            if (action is SearchAction.onBookClick) onBookClick(action.book)
            viewModel.onAction(action)
        }
    )
}

private val categoryLabels = BookCategory.entries.map { it.label }
private val ScreenPadding = 16.dp

/** Stateless UI: renders [state] and reports user events through [onAction]. */
@Composable
private fun SearchScreenContent(
    state: SearchState,
    onAction: (SearchAction) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFAF9F6))
            // Only status/nav bars + cutout — NOT gesture insets (they eat the side space)
            .windowInsetsPadding(WindowInsets.safeDrawing)
    ) {
        Spacer(modifier = Modifier.height(12.dp))

        BookSearchComponent(
            value = state.searchedText,
            onValueChange = { onAction(SearchAction.onSearchQueryChange(it)) },
            modifier = Modifier.padding(horizontal = ScreenPadding)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Full width so chips scroll to the screen edge instead of being clipped
        CategoryChipRow(
            categories = categoryLabels,
            selectedIndex = state.selectedTab,
            onCategoryClick = { onAction(SearchAction.onTabChange(it)) },
            contentPadding = PaddingValues(horizontal = ScreenPadding)
        )

        Spacer(modifier = Modifier.height(8.dp))

        when {
            state.isLoading -> LoadingIndicator()

            state.error != null -> ErrorView(
                message = state.error.asString(),
                onRetry = { onAction(SearchAction.onRetryClick) }
            )

            state.searchedBooks.isEmpty() -> EmptyView(
                message = if (state.searchedText.isBlank()) {
                    "No books found in this category"
                } else {
                    "No books found for \"${state.searchedText}\""
                }
            )

            else -> BookGrid(
                books = state.searchedBooks,
                favoriteIds = state.favoriteBooks.mapTo(HashSet()) { it.id },
                onBookClick = { onAction(SearchAction.onBookClick(it)) },
                onFavoriteClick = { onAction(SearchAction.onFavoriteClick(it)) }
            )
        }
    }
}

@Composable
private fun BookGrid(
    books: List<Book>,
    favoriteIds: Set<String>,
    onBookClick: (Book) -> Unit,
    onFavoriteClick: (Book) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(
            start = ScreenPadding,
            end = ScreenPadding,
            top = 4.dp,
            bottom = 16.dp
        ),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(items = books, key = { it.id }) { book ->
            BookListItem(
                book = book,
                isFavorite = book.id in favoriteIds,
                onClick = onBookClick,
                onFavoriteClick = onFavoriteClick
            )
        }
    }
}

@Preview
@Composable
private fun SearchScreenPreview() {
    SearchScreenContent(
        state = SearchState(
            searchedText = "habits",
            searchedBooks = listOf(
                Book(
                    id = "1",
                    title = "Atomic Habits",
                    imageUrl = "",
                    authors = listOf("James Clear"),
                    description = null,
                    languages = listOf("eng"),
                    firstPublishYear = "2018",
                    averageRating = 4.8,
                    ratingCount = 2500,
                    numPages = 320,
                    numEditions = 20
                )
            )
        ),
        onAction = {}
    )
}
