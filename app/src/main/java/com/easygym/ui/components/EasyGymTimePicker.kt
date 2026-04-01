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
import java.time.LocalTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EasyGymTimePicker(
    value: LocalTime,
    label: String,
    isError: Boolean = false,
    onTimeSelected: (LocalTime) -> Unit,
) {
    val timeState = rememberTimePickerState(
        initialHour = value.hour,
        initialMinute = value.minute,
        is24Hour = true
    )

    var showTimePickerDialog by remember { mutableStateOf(false) }

    Column {
        Text(
            text = label.uppercase(),
            style = MaterialTheme.typography.labelLarge,
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
            value = "%02d:%02d".format(value.hour, value.minute),
            onValueChange = {},
            readOnly = true,
            isError = isError,
            placeholder = {
                Text(
                    text = "HH:MM",
                    color = LocalColors.current.surface3,
                )
            },
            trailingIcon = {
                TextButton(onClick = { showTimePickerDialog = true }) {
                    Text(
                        text = "Seleziona Ora",
                        color = LocalColors.current.red,
                    )
                }
            },
            shape = RoundedCornerShape(12.dp),
        )

        // Time Picker Dialog
        if (showTimePickerDialog) {
            DatePickerDialog( // riusiamo il dialog Material
                onDismissRequest = { showTimePickerDialog = false },
                confirmButton = {
                    TextButton(
                        onClick = {
                            onTimeSelected(LocalTime.of(timeState.hour, timeState.minute))
                            showTimePickerDialog = false
                        }
                    ) {
                        Text(text = "OK", color = LocalColors.current.text)
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showTimePickerDialog = false }) {
                        Text(text = "Cancel", color = LocalColors.current.text)
                    }
                }
            ) {
                TimePicker(state = timeState)
            }
        }
    }
}