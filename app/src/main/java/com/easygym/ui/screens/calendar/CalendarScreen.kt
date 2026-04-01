package com.easygym.ui.screens.calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.easygym.ui.components.EasyGymCalendar
import com.easygym.ui.components.EasyGymCalendarEventCard
import com.easygym.ui.components.EasyGymFAB
import com.easygym.ui.theme.LocalColors
import com.easygym.utils.enums.EventType
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@Composable
fun CalendarScreen(
    viewModel: CalendarViewModel = hiltViewModel(),
    onNavigateToCreateEvent: () -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val timeFormatter = DateTimeFormatter.ofPattern("HH:mm")

    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = innerPadding.calculateTopPadding())
                .padding(horizontal = 18.dp)
        ) {

            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "Calendario",
                    style = MaterialTheme.typography.headlineLarge,
                    color = LocalColors.current.text
                )
                EasyGymFAB(onClick = onNavigateToCreateEvent, hasShadow = true)
            }

            state.errorMessage?.let {
                Text(text = it, style = MaterialTheme.typography.bodyMedium)
            }

            EasyGymCalendar(
                onMonthChanged = viewModel::onMonthChanged,
            ) { day, inMonth, date ->

                // UI per la singola cella del calendario

                val isSelected = inMonth && date == state.selectedDay
                val isToday = date == LocalDate.now()
                val isCompetitionDay = inMonth && date in state.competitionDates
                val hasEvent = inMonth && date in state.datesWithEvent

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .aspectRatio(1f)
                        .padding(2.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(
                            when {
                                isSelected -> LocalColors.current.red
                                isToday -> LocalColors.current.redDim.copy(alpha = 0.3f)
                                isCompetitionDay -> LocalColors.current.purpleDim.copy(alpha = 0.3f)
                                else -> Color.Transparent
                            }
                        )
                        .then(
                            if (inMonth) Modifier.clickable {
                                viewModel.onDaySelected(date)
                            } else Modifier
                        ),
                    contentAlignment = Alignment.Center,
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            "$day",
                            style = MaterialTheme.typography.labelMedium,
                            color = when {
                                isSelected -> LocalColors.current.text
                                isCompetitionDay -> LocalColors.current.purple
                                !inMonth -> LocalColors.current.gray.copy(alpha = 0.4f)
                                else -> LocalColors.current.graySoft
                            },
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                        )
                        if (hasEvent) {
                            Spacer(Modifier.height(1.dp))
                            Box(
                                Modifier
                                    .size(4.dp)
                                    .clip(CircleShape)
                                    .background(
                                        if (isSelected) LocalColors.current.text else LocalColors.current.green
                                    )
                            )
                        }
                    }
                }
            }

            Spacer(Modifier.height(10.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(14.dp)) {
                EventType.entries.forEach { eventType ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(5.dp)
                    ) {
                        Box(
                            Modifier
                                .size(7.dp)
                                .clip(CircleShape)
                                .background(
                                    when (eventType) {
                                        EventType.TRAINING -> LocalColors.current.blue
                                        EventType.COMPETITION -> LocalColors.current.purple
                                        EventType.OTHER -> LocalColors.current.amber
                                    }
                                )
                        )
                        Text(
                            text = eventType.label,
                            style = MaterialTheme.typography.labelSmall,
                            color = LocalColors.current.graySoft
                        )
                    }
                }
            }

            Spacer(Modifier.height(12.dp))

            Text(
                text = state.selectedDayLabel,
                style = MaterialTheme.typography.titleLarge,
                color = LocalColors.current.text
            )

            Spacer(Modifier.height(8.dp))

            if (state.selectedDayEvents.isEmpty()) {
                Text(
                    "Nessun evento",
                    style = MaterialTheme.typography.bodyMedium,
                    color = LocalColors.current.gray,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            } else {
                LazyColumn {
                    items(state.selectedDayEvents.size) { index ->
                        val event = state.selectedDayEvents[index]

                        val startTime = event.startDateTime.atZone(ZoneId.systemDefault()).format(timeFormatter)
                        val endTime = event.endDateTime.atZone(ZoneId.systemDefault()).format(timeFormatter)

                        EasyGymCalendarEventCard(
                            "$startTime\n$endTime",
                            event.title,
                            "${event.groupName} · ${event.location}",
                            when (event.eventType) {
                                EventType.TRAINING -> LocalColors.current.blue
                                EventType.COMPETITION -> LocalColors.current.purple
                                EventType.OTHER -> LocalColors.current.amber
                            },
                        )
                        Spacer(Modifier.height(8.dp))
                    }
                }
            }
        }
    }
}
