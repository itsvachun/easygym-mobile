package com.easygym.ui.screens.calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.easygym.ui.components.NavArrow
import com.easygym.ui.theme.LocalColors
import java.time.LocalDate

@Composable
fun EasyGymCalendar(
    state: CalendarState,
    onPreviousMonth: () -> Unit,
    onNextMonth: () -> Unit,
    onDaySelected: (LocalDate) -> Unit,
) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        NavArrow("‹", onClick = onPreviousMonth)
        Text(state.monthLabel, style = MaterialTheme.typography.titleLarge, color = LocalColors.current.text)
        NavArrow("›", onClick = onNextMonth)
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
                val isSelected = inMonth && date == state.selectedDay
                val isToday = date == state.today
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
                                onDaySelected(date)
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
        }
    }
}
