package com.easygym.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.easygym.ui.theme.LocalColors
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneOffset

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EasyGymDatePicker(
    value: LocalDate,
    label: String,
    isError: Boolean = false,
    onDateSelected: (LocalDate) -> Unit,
) {
    val calendarState = rememberDatePickerState(
        initialSelectedDateMillis = value.atStartOfDay(ZoneOffset.UTC).toEpochSecond() * 1000
    )
    var showDatePickerDialog by remember { mutableStateOf(false) }

    Column {
        Text(
            text = label.uppercase(),
            style = MaterialTheme.typography.titleMedium,
            color = LocalColors.current.surface4,
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
                    color = LocalColors.current.surface3,
                    shape = RoundedCornerShape(12.dp),
                )
                .background(LocalColors.current.surface1),
            value = value.toString(),
            onValueChange = {},
            readOnly = true,
            isError = isError,
            placeholder = {
                Text(
                    text = "YYYY-MM-DD",
                    color = LocalColors.current.surface3,
                )
            },
            trailingIcon = {
                TextButton(onClick = { showDatePickerDialog = true }) {
                    Text(
                        text = "Select Date",
                        color = LocalColors.current.red,
                    )
                }
            },
            shape = RoundedCornerShape(12.dp),
        )

        // Show Date Picker Dialog
        if (showDatePickerDialog) {
            DatePickerDialog(
                onDismissRequest = { showDatePickerDialog = false },
                confirmButton = {
                    TextButton(
                        onClick = {
                            val selectedLocalDate = Instant.ofEpochMilli(calendarState.selectedDateMillis!!)
                                .atZone(ZoneOffset.UTC)
                                .toLocalDate()
                            onDateSelected(selectedLocalDate)
                            showDatePickerDialog = false
                        }
                    ) {
                        Text(text = "OK", color = LocalColors.current.text)
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showDatePickerDialog = false }) {
                        Text(text = "Cancel", color = LocalColors.current.text)
                    }
                }
            ) {
                DatePicker(
                    calendarState
                )
            }
        }
    }
}