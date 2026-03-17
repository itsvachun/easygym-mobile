package com.easygym.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.easygym.ui.theme.LocalColors

@Composable
fun IconButton(
    text: String,
    icon: ImageVector? = null,
    height: Int = 56,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(14.dp))
            .height(height.dp)
            .fillMaxWidth()
            .background(LocalColors.current.surface1)
            .border(
                1.dp,
                LocalColors.current.surface3.copy(alpha = 0.3f),
                RoundedCornerShape(14.dp)
            )
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            icon?.let {
                Icon(
                    imageVector = icon,
                    contentDescription = text
                )
            }
            Text(
                text = text,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}