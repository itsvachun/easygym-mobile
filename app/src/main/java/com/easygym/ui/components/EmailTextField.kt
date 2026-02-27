package com.easygym.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun EmailTextField(
    value: String,
    isError: Boolean = false,
    onValueChange: (String) -> Unit,
) {
    Column {
        Text(
            text = "EMAIL",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.outlineVariant,
            fontWeight = FontWeight.Bold,
            letterSpacing = 2.sp,
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .border(
                    width = 1.5.dp,
                    color = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f),
                    shape = RoundedCornerShape(12.dp),
                )
                .background(MaterialTheme.colorScheme.surface),
            value = value,
            onValueChange = onValueChange,
            singleLine = true,
            isError = isError,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Done
            ),
            shape = RoundedCornerShape(12.dp),
            placeholder = {
                Text(
                    text = "example@mail.com",
                    color = MaterialTheme.colorScheme.outline,
                )
            }
        )
    }
}
