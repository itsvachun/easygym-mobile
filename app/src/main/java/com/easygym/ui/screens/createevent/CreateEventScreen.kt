package com.easygym.ui.screens.createevent

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.easygym.ui.components.*
import com.easygym.ui.theme.LocalColors
import com.easygym.utils.enums.EventType
import java.time.ZoneId

@Composable
fun CreateEventScreen(
    viewModel: CreateEventViewModel = hiltViewModel(),
    onBack: () -> Unit = {},
    modifier: Modifier = Modifier,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()


    listOf(
        Triple("🥋", "Allenamento", LocalColors.current.blueDim to LocalColors.current.blue),
        Triple("🏆", "Gara", LocalColors.current.purpleDim to LocalColors.current.purple),
        Triple("⭐", "Evento", LocalColors.current.amberDim to LocalColors.current.amber)
    )

    Scaffold { innerPadding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(LocalColors.current.bg)
                .padding(innerPadding)
                .padding(horizontal = 18.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
//        BackButton(label = "Calendario", onClick = onBack)
            Text("Nuovo evento", style = MaterialTheme.typography.headlineLarge, color = LocalColors.current.text)
            EasyGymTextField(
                value = state.event.title,
                label = "Titolo",
                placeholder = "Es. Torneo Invernale…",
                onValueChange = { viewModel.updateEvent(title = it) },
            )

            // Type picker
            Column {
                Text(
                    text = "TIPOLOGIA",
                    style = MaterialTheme.typography.labelLarge,
                    color = LocalColors.current.surface4,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 2.sp
                )
                Spacer(Modifier.height(6.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    EventType.entries.forEachIndexed { i, type ->
                        val isSelected = state.event.eventType == type
                        val color = when (type) {
                            EventType.TRAINING -> LocalColors.current.blue
                            EventType.COMPETITION -> LocalColors.current.purple
                            EventType.OTHER -> LocalColors.current.amber
                        }
                        EasyGymSegmentedButton(
                            modifier = Modifier.weight(1f),
                            text = type.label,
                            icon = when (type) {
                                EventType.TRAINING -> "🥋"
                                EventType.COMPETITION -> "🏆"
                                EventType.OTHER -> "⭐"
                            },
                            borderColor = if (isSelected) color else LocalColors.current.surface3,
                            textColor = if (isSelected) color else LocalColors.current.surface4,
                            onClick = { viewModel.updateEvent(eventType = type) },
                        )
                    }
                }
            }
            EasyGymDatePicker(
                value = state.selectedDate,
                label = "Data",
                onDateSelected = { viewModel.updateEvent(selectedDate = it) },
            )
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                Box(Modifier.weight(1f)) {
                    EasyGymTimePicker(
                        value = state.event.startDateTime.atZone(ZoneId.systemDefault()).toLocalTime(),
                        label = "Inizio",
                        onTimeSelected = { viewModel.updateEvent(startTime = it) }
                    )
                }
                Box(Modifier.weight(1f)) {
                    EasyGymTimePicker(
                        value = state.event.endDateTime.atZone(ZoneId.systemDefault()).toLocalTime(),
                        label = "Fine",
                        onTimeSelected = { viewModel.updateEvent(endTime = it) },
                    )
                }
            }
            EasyGymTextField(
                value = state.event.location,
                label = "Luogo",
                placeholder = "Palestra, indirizzo…",
                onValueChange = { viewModel.updateEvent(location = it) },
            )
            Text(
                text = "GRUPPI COINVOLTI",
                style = MaterialTheme.typography.labelLarge,
                color = LocalColors.current.surface4,
                fontWeight = FontWeight.Bold,
                letterSpacing = 2.sp
            )
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                state.groups.forEach { group ->
                    EasyGymFilterButton(
                        text = group.name,
                        onClick = { viewModel.onGroupSelected(group) },
                        selected = state.selectedGroups.contains(group),
                    )
                }
            }
            EasyGymDescriptionField(
                value = state.event.description,
                label = "Descrizione",
                placeholder = "Inserisci una descrizione...",
                onValueChange = { viewModel.updateEvent(description = it) },
            )
            Spacer(Modifier.fillMaxHeight())
            PrimaryButton(text = "Crea evento", onClick = viewModel::createEvent)
        }
    }

}