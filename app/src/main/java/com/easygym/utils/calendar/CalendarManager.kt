package com.easygym.utils.calendar

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.time.LocalDate
import java.time.YearMonth
import javax.inject.Inject

data class CalendarManagerState(
    val calendarRows: List<List<Triple<Int, Boolean, LocalDate>>> = emptyList(),
    val monthLabel: String = "",
)

@HiltViewModel
class CalendarManager @Inject constructor(
    private val calendarHelper: CalendarHelper,
) : ViewModel() {
    private val _state = MutableStateFlow(CalendarManagerState())
    val state: StateFlow<CalendarManagerState> = _state.asStateFlow()

    private var currentMonth: YearMonth = YearMonth.now()

    init {
        updateState()
    }

    fun onPreviousMonth(onMonthChanged: (YearMonth) -> Unit) {
        currentMonth = currentMonth.minusMonths(1)
        onMonthChanged(currentMonth)
        updateState()
    }

    fun onNextMonth(onMonthChanged: (YearMonth) -> Unit) {
        currentMonth = currentMonth.plusMonths(1)
        onMonthChanged(currentMonth)
        updateState()
    }

    private fun updateState() = _state.update { currentState ->
        currentState.copy(
            calendarRows = calendarHelper.generateCalendarRows(currentMonth),
            monthLabel = calendarHelper.getMonthLabel(currentMonth),
        )
    }
}