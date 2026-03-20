package com.easygym.ui.screens.calendar

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.*

enum class EventType(val label: String) {
    TRAINING("Allenamento"),
    COMPETITION("Gara"),
    OTHER("Altro")
}

data class CalendarEvent(
    val id: String,
    val date: LocalDate,
    val timeStart: String,
    val timeEnd: String,
    val title: String,
    val subtitle: String,
    val type: EventType,
)

data class CalendarState(
    val displayedMonth: YearMonth = YearMonth.now(),
    val selectedDay: LocalDate = LocalDate.now(),
    val today: LocalDate = LocalDate.now(),
    val events: List<CalendarEvent> = emptyList(),
) {
    val dayOffset: Int = displayedMonth.atDay(1).dayOfWeek.value - 1

    val daysInMonth: Int = displayedMonth.lengthOfMonth()

    val prevMonthTailDays: List<Int>
        get() {
            if (dayOffset == 0) return emptyList()
            val prevLen = displayedMonth.minusMonths(1).lengthOfMonth()
            return (prevLen - dayOffset + 1..prevLen).toList()
        }

    val eventsByDate: Map<LocalDate, List<CalendarEvent>> = events.groupBy { it.date }

    val datesWithEvent: Set<LocalDate> = eventsByDate.keys

    val garaDates: Set<LocalDate> = events
        .filter { it.type == EventType.COMPETITION }
        .map { it.date }.toSet()

    val calendarRows = buildList {
        prevMonthTailDays.forEach { d ->
            add(Triple(d, false, displayedMonth.minusMonths(1).atDay(d)))
        }
        (1..daysInMonth).forEach { d ->
            add(Triple(d, true, displayedMonth.atDay(d)))
        }
        val next = displayedMonth.plusMonths(1)
        repeat(42 - size) { i -> add(Triple(i + 1, false, next.atDay(i + 1))) }
    }.chunked(7)

    val monthLabel: String
        get() {
            val name = displayedMonth.month
                .getDisplayName(TextStyle.FULL, Locale.ITALIAN)
                .replaceFirstChar { it.uppercaseChar() }
            return "$name ${displayedMonth.year}"
        }

    val selectedDayLabel: String
        get() {
            val dow = selectedDay.dayOfWeek
                .getDisplayName(TextStyle.FULL, Locale.ITALIAN)
                .replaceFirstChar { it.uppercaseChar() }
            val mon = selectedDay.month
                .getDisplayName(TextStyle.FULL, Locale.ITALIAN)
                .replaceFirstChar { it.uppercaseChar() }
            return "$dow ${selectedDay.dayOfMonth} $mon"
        }

    val selectedDayEvents: List<CalendarEvent>
        get() = eventsByDate[selectedDay] ?: emptyList()
}

class CalendarViewModel : ViewModel() {

    private val _state = MutableStateFlow(CalendarState())
    val state: StateFlow<CalendarState> = _state.asStateFlow()

    init {
        loadEvents()
    }

    fun onPreviousMonth() {
        _state.update { state ->
            val newMonth = state.displayedMonth.minusMonths(1)
            state.copy(
                displayedMonth = newMonth,
                selectedDay = defaultSelectionForMonth(newMonth, state.today),
            )
        }
    }

    fun onNextMonth() {
        _state.update { state ->
            val newMonth = state.displayedMonth.plusMonths(1)
            state.copy(
                displayedMonth = newMonth,
                selectedDay = defaultSelectionForMonth(newMonth, state.today),
            )
        }
    }

    fun onDaySelected(date: LocalDate) {
        _state.update { it.copy(selectedDay = date) }
    }

    private fun defaultSelectionForMonth(month: YearMonth, today: LocalDate): LocalDate =
        if (YearMonth.from(today) == month) today else month.atDay(1)

    private fun loadEvents() {
        val base = LocalDate.now()
        val fakeEvents = listOf(
            CalendarEvent(
                id = "ev1",
                date = base,
                timeStart = "18:30", timeEnd = "20:30",
                title = "All. Agonisti", subtitle = "Cafasse · 13 att.",
                type = EventType.TRAINING,
            ),
            CalendarEvent(
                id = "ev2",
                date = base,
                timeStart = "17:30", timeEnd = "18:30",
                title = "All. Secondo turno", subtitle = "Cafasse · 10 att.",
                type = EventType.TRAINING,
            ),
            CalendarEvent(
                id = "ev3",
                date = base.plusDays(2),
                timeStart = "19:00", timeEnd = "21:00",
                title = "All. Agonisti", subtitle = "Cafasse · 8 att.",
                type = EventType.TRAINING,
            ),
            CalendarEvent(
                id = "ev4",
                date = base.plusDays(5),
                timeStart = "09:00", timeEnd = "18:00",
                title = "Gara Regionale", subtitle = "Torino · 22 att.",
                type = EventType.COMPETITION,
            ),
            CalendarEvent(
                id = "ev5",
                date = base.plusDays(7),
                timeStart = "17:00", timeEnd = "19:00",
                title = "Allenamento Speciale", subtitle = "Cafasse · 15 att.",
                type = EventType.OTHER,
            ),
            CalendarEvent(
                id = "ev6",
                date = base.plusDays(9),
                timeStart = "17:30", timeEnd = "18:30",
                title = "All. Secondo turno", subtitle = "Cafasse · 11 att.",
                type = EventType.TRAINING,
            ),
        )
        _state.update { it.copy(events = fakeEvents) }
    }
}