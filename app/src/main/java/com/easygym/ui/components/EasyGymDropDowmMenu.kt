package com.easygym.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.easygym.ui.theme.LocalColors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> EasyGymDropDownMenu(
    items: List<T>,
    selectedItem: T?,
    onItemSelected: (T) -> Unit,
    label: String,
    itemLabel: (T) -> String
) {
    var expanded by remember { mutableStateOf(false) }


    Column {
        Text(
            text = label.uppercase(),
            style = MaterialTheme.typography.labelLarge,
            color = LocalColors.current.surface4,
            fontWeight = FontWeight.Bold,
            letterSpacing = 2.sp
        )

        Spacer(modifier = Modifier.height(8.dp))

        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded },
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .border(
                    width = 1.5.dp,
                    color = LocalColors.current.surface3,
                    shape = RoundedCornerShape(12.dp)
                )
        ) {
            OutlinedTextField(
                value = selectedItem?.let { itemLabel(it) } ?: "Seleziona $label",
                onValueChange = {},
                readOnly = true,
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor(),
                placeholder = {
                    Text(
                        text = "Seleziona $label",
                        color = LocalColors.current.surface3
                    )
                },
                shape = RoundedCornerShape(12.dp),
                colors = ExposedDropdownMenuDefaults.textFieldColors(
                    unfocusedContainerColor = LocalColors.current.surface1,
                    focusedContainerColor = LocalColors.current.surface1,
                )
            )

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                containerColor = Color.Transparent,
                modifier = Modifier.fillMaxWidth(fraction = 0.885f)
            ) {
                items.forEach { item ->
                    DropdownMenuItem(
                        text = {
                            Text(
                                itemLabel(item),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp)
                            )
                        },
                        onClick = {
                            onItemSelected(item)
                            expanded = false
                        },
                        modifier = Modifier
                            .clip(RoundedCornerShape(12.dp))
                            .background(LocalColors.current.surface1)
                            .border(
                                width = 1.dp,
                                color = LocalColors.current.surface3.copy(alpha = 0.3f),
                                shape = RoundedCornerShape(12.dp)
                            )
                    )
                }
            }

        }
    }
}