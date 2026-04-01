package com.easygym.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.easygym.ui.theme.LocalColors
import com.easygym.utils.calendar.CalendarManager
import java.time.LocalDate
import java.time.YearMonth

@Composable
fun EasyGymCalendar(
    calendarManager: CalendarManager = hiltViewModel(),
    onMonthChanged: (YearMonth) -> Unit,
    cellBuilder: @Composable (Int, Boolean, LocalDate) -> Unit
) {
    val state by calendarManager.state.collectAsStateWithLifecycle()

    Row(
        Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        EasyGymCalendarNavArrow(
            symbol = "‹",
            onClick = { calendarManager.onPreviousMonth(onMonthChanged) }
        )

        Text(
            text = state.monthLabel,
            style = MaterialTheme.typography.titleLarge,
            color = LocalColors.current.text
        )

        EasyGymCalendarNavArrow(
            symbol = "›",
            onClick = { calendarManager.onNextMonth(onMonthChanged) }
        )
    }

    Spacer(Modifier.height(8.dp))

    Row(Modifier.fillMaxWidth()) {
        listOf("LUN", "MAR", "MER", "GIO", "VEN", "SAB", "DOM").forEach { d ->
            Text(
                d,
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.labelSmall,
                color = LocalColors.current.gray,
                fontWeight = FontWeight.Bold,
            )
        }
    }

    Spacer(Modifier.height(4.dp))

    state.calendarRows.forEach { row ->
        Row(Modifier.fillMaxWidth()) {
            row.forEach { (day, inMonth, date) ->
                cellBuilder(day, inMonth, date)
            }
        }
    }
}
