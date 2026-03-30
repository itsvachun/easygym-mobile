package com.easygym.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.easygym.ui.theme.LocalColors

@Composable
fun EasyGymCalendarEventCard(
    time: String,
    title: String,
    subtitle: String,
    barColor: Color,
    modifier: Modifier = Modifier,
) {

    Row(
        modifier = modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Text(
            time,
            style = MaterialTheme.typography.labelSmall,
            color = LocalColors.current.graySoft,
            textAlign = TextAlign.End,
            modifier = Modifier
                .width(38.dp)
                .padding(top = 2.dp),
            lineHeight = 13.sp
        )
        Box(
            Modifier
                .width(3.dp)
                .height(52.dp)
                .clip(RoundedCornerShape(2.dp))
                .background(barColor)
        )
        Column(
            modifier = Modifier
                .weight(1f)
                .clip(RoundedCornerShape(12.dp))
                .background(LocalColors.current.surface1)
                .border(1.dp, LocalColors.current.surface2, RoundedCornerShape(12.dp))
                .padding(horizontal = 12.dp, vertical = 9.dp),
        ) {
            Text(
                title,
                style = MaterialTheme.typography.bodyMedium,
                color = LocalColors.current.text,
                fontWeight = FontWeight.Medium
            )
            Text(subtitle, style = MaterialTheme.typography.labelSmall, color = LocalColors.current.gray)
        }
    }
}