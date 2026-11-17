package com.example.litera.book.presentation.details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.litera.book.presentation.details.components.BookCover
import com.example.litera.book.presentation.details.components.BookInfoSection
import com.example.litera.book.presentation.details.components.ReadingStatusSelector
import com.example.litera.readinglist.domain.model.ReadingStatus
import com.example.litera.core.presentation.components.ErrorView
import com.example.litera.core.presentation.components.LoadingIndicator
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun BookDetailsScreen(
    workId: String,
    onBackClick: () -> Unit,
    viewModel: BookDetailsViewModel = koinViewModel { parametersOf(workId) }
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    BookDetailsContent(
        state = state,
        onBackClick = onBackClick,
        onRetryClick = viewModel::onRetryClick,
        onReadingStatusClick = viewModel::onReadingStatusClick
    )
}

@Composable
private fun BookDetailsContent(
    state: BookDetailsState,
    onBackClick: () -> Unit,
    onRetryClick: () -> Unit,
    onReadingStatusClick: (ReadingStatus) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFAF9F6))
            .windowInsetsPadding(WindowInsets.safeDrawing)
    ) {
        // Top bar with back button
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBackClick) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = Color(0xFF18213A)
                )
            }
        }

        when {
            state.isLoading -> LoadingIndicator()

            state.error != null -> ErrorView(
                message = state.error.asString(),
                onRetry = onRetryClick
            )

            state.book != null -> Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(8.dp))
                BookCover(
                    imageUrl = state.book.imageUrl,
                    contentDescription = state.book.title
                )
                Spacer(modifier = Modifier.height(24.dp))
                ReadingStatusSelector(
                    selected = state.readingStatus,
                    onStatusClick = onReadingStatusClick
                )
                Spacer(modifier = Modifier.height(24.dp))
                BookInfoSection(book = state.book)
                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}
