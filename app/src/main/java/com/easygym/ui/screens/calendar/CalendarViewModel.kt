package com.easygym.ui.screens.calendar

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.easygym.domain.model.Event
import com.easygym.domain.usecase.event.EventsUseCase
import com.easygym.domain.usecase.event.FetchEventsUseCase
import com.easygym.utils.calendar.CalendarHelper
import com.easygym.utils.enums.EventType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.YearMonth
import java.time.ZoneId
import javax.inject.Inject

data class CalendarState(
    val selectedDay: LocalDate = LocalDate.now(),
    val events: List<Event> = emptyList(),
    val selectedDayLabel: String = "",
    val selectedDayEvents: List<Event> = emptyList(),
    val datesWithEvent: Set<LocalDate> = emptySet(),
    val competitionDates: Set<LocalDate> = emptySet(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

@HiltViewModel
class CalendarViewModel @Inject constructor(
    private val calendarHelper: CalendarHelper,
    private val eventsUseCase: EventsUseCase,
    private val fetchEventsUseCase: FetchEventsUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(CalendarState())
    val state: StateFlow<CalendarState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            eventsUseCase().collect { domainEvents ->
                _state.update { it.copy(events = domainEvents) }
                updateState()
            }
        }

        loadEventsForMonth(YearMonth.now())
    }

    fun onMonthChanged(month: YearMonth) {
        _state.update { it.copy(selectedDay = calendarHelper.defaultSelectionForMonth(month)) }
        loadEventsForMonth(month)
        updateState()
    }

    fun onDaySelected(date: LocalDate) {
        _state.update { it.copy(selectedDay = date) }
        updateState()
    }

    private fun loadEventsForMonth(month: YearMonth) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            val from = calendarHelper.getFirstMomentOfMonth(month)
            val to = calendarHelper.getLastMomentOfMonth(month)
            _state.update {
                it.copy(
                    errorMessage = fetchEventsUseCase(from, to).exceptionOrNull()?.message,
                    isLoading = false
                )
            }
        }
    }

    private fun updateState() {
        _state.update { state ->
            val eventsByDate = state.events.groupBy {
                it.startDateTime.atZone(ZoneId.systemDefault()).toLocalDate()
            }

            state.copy(
                selectedDayLabel = calendarHelper.getDayLabel(state.selectedDay),
                selectedDayEvents = eventsByDate[state.selectedDay] ?: emptyList(),
                datesWithEvent = eventsByDate.keys,
                competitionDates = state.events
                    .filter { it.eventType == EventType.COMPETITION }
                    .map { it.startDateTime.atZone(ZoneId.systemDefault()).toLocalDate() }.toSet()
            )
        }
    }
}
