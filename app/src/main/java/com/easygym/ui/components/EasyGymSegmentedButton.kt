package com.easygym.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.easygym.ui.theme.LocalColors

@Composable
fun EasyGymSegmentedButton(
    modifier: Modifier = Modifier,
    text: String,
    icon: String,
    borderColor: Color? = null,
    textColor: Color? = null,
    backgroundColor: Color? = null,
    onClick: () -> Unit
) {
    val newBorderColor = borderColor ?: LocalColors.current.surface3.copy(alpha = 0.3f)
    val newTextColor = textColor ?: LocalColors.current.text
    val newBackgroundColor = backgroundColor ?: LocalColors.current.surface1

    Column(
        modifier = modifier
            .clip(shape = RoundedCornerShape(10.dp))
            .background(color = newBackgroundColor)
            .border(
                width = 1.5.dp,
                color = newBorderColor,
                shape = RoundedCornerShape(10.dp)
            )
            .clickable(onClick = onClick)
            .padding(vertical = 10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(icon, fontSize = 15.sp)
        Spacer(Modifier.height(3.dp))
        Text(
            text,
            style = MaterialTheme.typography.labelMedium,
            color = newTextColor,
            fontWeight = FontWeight.Bold
        )
    }
}