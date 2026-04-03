package com.easygym.ui.screens.createevent

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.easygym.domain.model.Event
import com.easygym.domain.model.Group
import com.easygym.domain.usecase.GetGroupsUseCase
import com.easygym.domain.usecase.event.CreateEventUseCase
import com.easygym.utils.enums.EventType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.*
import javax.inject.Inject

data class CreateEventState(
    val event: Event = Event(
        title = "",
        eventType = EventType.TRAINING,
        description = "",
        startDateTime = Instant.now(),
        endDateTime = Instant.now(),
        location = "",
    ),
    val selectedDate: LocalDate = LocalDate.now(),
    val groups: List<Group> = emptyList(),
    val selectedGroups: List<Group> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
)

@HiltViewModel
class CreateEventViewModel @Inject constructor(
    private val groupsUseCase: GetGroupsUseCase,
    private val createEventUseCase: CreateEventUseCase,
) : ViewModel() {
    private val _state = MutableStateFlow(CreateEventState())
    val state: StateFlow<CreateEventState> = _state.asStateFlow()


    init {
        loadGroups()
    }

    private fun loadGroups() {
        viewModelScope.launch {
            try {
                val groups: List<Group> = groupsUseCase()
                _state.update { it.copy(groups = groups) }
            } catch (e: Exception) {
                _state.update { it.copy(errorMessage = "Errore nel caricamento gruppi") }
            }
        }
    }

    fun onGroupSelected(group: Group) {
        _state.update {
            it.copy(
                selectedGroups = when {
                    it.selectedGroups.contains(group) -> it.selectedGroups - group
                    else -> it.selectedGroups + group
                }
            )
        }
    }

    fun updateEvent(
        title: String? = null,
        eventType: EventType? = null,
        description: String? = null,
        selectedDate: LocalDate? = null,
        startTime: LocalTime? = null,
        endTime: LocalTime? = null,
        location: String? = null,
    ) = _state.update {
        it.copy(
            event = it.event.copy(
                title = title ?: it.event.title,
                eventType = eventType ?: it.event.eventType,
                description = description ?: it.event.description,
                startDateTime =
                    if (startTime != null) LocalDateTime.of(it.selectedDate, startTime)
                        .atZone(ZoneId.systemDefault())
                        .toInstant()
                    else it.event.startDateTime,
                endDateTime =
                    if (endTime != null) LocalDateTime.of(it.selectedDate, endTime)
                        .atZone(ZoneId.systemDefault())
                        .toInstant()
                    else it.event.endDateTime,
                location = location ?: it.event.location,
            ),
            selectedDate = selectedDate ?: it.selectedDate
        )
    }

    fun createEvent() {
        _state.update { currentState ->
            var errorMessage: String? = null
            currentState.selectedGroups.forEach { group ->
                viewModelScope.launch {
                    errorMessage = createEventUseCase(group.id, _state.value.event).exceptionOrNull()?.message
                    delay(1000)
                }
            }
            currentState.copy(errorMessage = errorMessage)
        }
    }
}