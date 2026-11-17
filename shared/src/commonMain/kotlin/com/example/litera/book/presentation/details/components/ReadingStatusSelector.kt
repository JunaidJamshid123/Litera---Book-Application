package com.example.litera.book.presentation.details.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.litera.readinglist.domain.model.ReadingStatus

/** Three buttons: Want to Read / Reading / Finished. Tapping the selected one again removes it. */
@Composable
fun ReadingStatusSelector(
    selected: ReadingStatus?,
    onStatusClick: (ReadingStatus) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        ReadingStatus.entries.forEach { status ->
            val isSelected = status == selected
            val shape = RoundedCornerShape(50)

            Box(
                modifier = Modifier
                    .weight(1f)
                    .clip(shape)
                    .background(if (isSelected) Color(0xFFFBE8CF) else Color.White)
                    .border(1.dp, if (isSelected) Color(0xFFFBE8CF) else Color(0xFFE6E4DF), shape)
                    .clickable { onStatusClick(status) }
                    .padding(vertical = 10.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = status.label,
                    fontSize = 13.sp,
                    fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal,
                    color = if (isSelected) Color(0xFF2B2117) else Color(0xFF777D8F),
                    textAlign = TextAlign.Center,
                    maxLines = 1
                )
            }
        }
    }
}
