package com.easygym.ui.screens.calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.easygym.ui.components.EasyGymFAB
import com.easygym.ui.components.EventCard
import com.easygym.ui.components.SectionHeader
import com.easygym.ui.theme.LocalColors

@Composable
fun CalendarScreen(
    viewModel: CalendarViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = innerPadding.calculateTopPadding())
                .padding(horizontal = 18.dp)
                .verticalScroll(rememberScrollState()),
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
                EasyGymFAB(onClick = {}, hasShadow = true)
            }

            EasyGymCalendar {}

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
                                        EventType.COMPETITION -> LocalColors.current.amber
                                        EventType.OTHER -> LocalColors.current.purple
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

            SectionHeader(state.selectedDayLabel)

            if (state.selectedDayEvents.isEmpty()) {
                Text(
                    "Nessun evento",
                    style = MaterialTheme.typography.bodyMedium,
                    color = LocalColors.current.gray,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            } else {
                state.selectedDayEvents.forEachIndexed { index, ev ->
                    EventCard(
                        "${ev.timeStart}\n${ev.timeEnd}",
                        ev.title,
                        ev.subtitle,
                        when (ev.type) {
                            EventType.TRAINING -> LocalColors.current.blue
                            EventType.COMPETITION -> LocalColors.current.amber
                            EventType.OTHER -> LocalColors.current.purple
                        },
                    )
                    if (index < state.selectedDayEvents.lastIndex) Spacer(Modifier.height(8.dp))
                }
            }
        }
    }
}