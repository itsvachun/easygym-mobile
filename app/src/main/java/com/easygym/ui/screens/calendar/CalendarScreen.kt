package com.easygym.ui.screens.calendar

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.easygym.ui.components.PrimaryFilterButton
import com.easygym.ui.components.SecondaryFilterButton

@Composable
fun CalendarScreen() {
    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            val lista = listOf("1", "2", "3")
            var selectedIndex by remember { mutableStateOf(0) }
            Text("Calendar Screen")
            SingleChoiceSegmentedButtonRow {
                lista.forEachIndexed { index, string ->
                    SegmentedButton(
                        selected = index == selectedIndex,
                        onClick = { selectedIndex = index },
                        shape = SegmentedButtonDefaults.itemShape(
                            index = index,
                            count = lista.size
                        )
                    ) {
                        Text(string)
                    }
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Box(modifier = Modifier.weight(1f)) {
                    PrimaryFilterButton(text = "prova", onClick = {})
                }
                Box(modifier = Modifier.weight(1f)) {
                    PrimaryFilterButton(text = "prova", onClick = {})
                }
                Box(modifier = Modifier.weight(1f)) {
                    SecondaryFilterButton(text = "prova", onClick = {})
                }

            }

        }
    }
}