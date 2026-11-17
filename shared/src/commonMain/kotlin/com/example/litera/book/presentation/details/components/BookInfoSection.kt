package com.example.litera.book.presentation.details.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.litera.book.domain.model.BookDetails

private val TextPrimary = Color(0xFF18213A)
private val TextSecondary = Color(0xFF777D8F)
private val ChipBackground = Color(0xFFFBE8CF)
private val ChipText = Color(0xFF2B2117)

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun BookInfoSection(
    book: BookDetails,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxWidth()) {

        Text(
            text = book.title,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = TextPrimary,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        if (book.firstPublishDate != null) {
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = "First published ${book.firstPublishDate}",
                fontSize = 13.sp,
                color = TextSecondary,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        SectionTitle("About this book")
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = book.description ?: "No description available.",
            fontSize = 14.sp,
            lineHeight = 21.sp,
            color = if (book.description != null) TextPrimary else TextSecondary
        )

        if (book.subjects.isNotEmpty()) {
            Spacer(modifier = Modifier.height(24.dp))
            SectionTitle("Subjects")
            Spacer(modifier = Modifier.height(10.dp))
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                book.subjects.take(MAX_SUBJECTS).forEach { subject ->
                    Text(
                        text = subject,
                        fontSize = 12.sp,
                        color = ChipText,
                        modifier = Modifier
                            .clip(RoundedCornerShape(50))
                            .background(ChipBackground)
                            .padding(horizontal = 12.dp, vertical = 6.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun SectionTitle(text: String) {
    Text(
        text = text,
        fontSize = 16.sp,
        fontWeight = FontWeight.Bold,
        color = TextPrimary
    )
}

private const val MAX_SUBJECTS = 12
