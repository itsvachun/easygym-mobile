package com.easygym.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.easygym.ui.theme.LocalColors

@Composable
fun SectionHeader(title: String, action: String = "", onAction: (() -> Unit)? = null, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier.fillMaxWidth().padding(bottom = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(title, style = MaterialTheme.typography.titleLarge, color = LocalColors.current.text)
        if (action.isNotEmpty() && onAction != null)
            Text(
                action,
                style = MaterialTheme.typography.labelMedium,
                color = LocalColors.current.red,
                modifier = Modifier.clickable(onClick = onAction)
            )
    }
}