package com.example.litera.favorites.presentation.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.litera.book.domain.model.Book
import com.example.litera.book.presentation.home.components.BookListItem

/** Same card as the search grid, always showing a filled heart (tap it to remove). */
@Composable
fun FavoriteBookItem(
    book: Book,
    onClick: (Book) -> Unit,
    onRemoveClick: (Book) -> Unit,
    modifier: Modifier = Modifier
) {
    BookListItem(
        book = book,
        modifier = modifier,
        isFavorite = true,
        onFavoriteClick = onRemoveClick,
        onClick = onClick
    )
}
