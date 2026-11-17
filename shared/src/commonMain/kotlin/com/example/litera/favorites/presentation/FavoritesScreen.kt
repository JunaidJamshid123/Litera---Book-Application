package com.example.litera.favorites.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.litera.book.domain.model.Book
import com.example.litera.core.presentation.components.EmptyView
import com.example.litera.core.presentation.components.LoadingIndicator
import com.example.litera.favorites.presentation.components.FavoriteBookItem
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun FavoritesScreen(
    onBookClick: (Book) -> Unit,
    viewModel: FavoritesViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    Column(modifier = Modifier.fillMaxSize()) {
        Text(
            text = "Favorites",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF18213A),
            modifier = Modifier.padding(16.dp)
        )

        when {
            state.isLoading -> LoadingIndicator()

            state.books.isEmpty() -> EmptyView(
                message = "Books you mark with ♥ will appear here."
            )

            else -> LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(start = 16.dp, end = 16.dp, bottom = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(items = state.books, key = { it.id }) { book ->
                    FavoriteBookItem(
                        book = book,
                        onClick = onBookClick,
                        onRemoveClick = viewModel::onRemoveClick
                    )
                }
            }
        }
    }
}
