package com.easygym.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.easygym.ui.theme.LocalColors

@Composable
fun EasyGymIconButton(
    text: String,
    iconText: String,
    height: Int = 56,
    borderColor: Color = LocalColors.current.surface3.copy(alpha = 0.3f),
    textColor: Color = LocalColors.current.text,
    backgroundColor: Color = LocalColors.current.surface1,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(14.dp))
            .height(height.dp)
            .fillMaxWidth()
            .background(backgroundColor)
            .border(
                width = 1.dp,
                color = borderColor,
                shape = RoundedCornerShape(14.dp)
            )
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = iconText,
                fontSize = 24.sp
            )

            Text(
                text = text,
                color = textColor,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}