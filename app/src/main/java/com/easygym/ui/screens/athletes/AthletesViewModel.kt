package com.easygym.ui.screens.athletes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.easygym.domain.model.Athlete
import com.easygym.domain.usecase.athlete.AthletesUseCase
import com.easygym.domain.usecase.athlete.FetchAthletesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class AthletesState(
    val isLoading: Boolean = false,
    val isFetchingNextPage: Boolean = false,
    val search: String = "",
    val athletes: List<Athlete> = listOf(),
    val errorMessage: String? = null
)

@OptIn(FlowPreview::class)
@HiltViewModel
class AthletesViewModel @Inject constructor(
    private val athletesUseCase: AthletesUseCase,
    private val fetchAthletesUseCase: FetchAthletesUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(AthletesState())
    val state: StateFlow<AthletesState> = _state.asStateFlow()

    private var currentPage = 0
    private var isLastPage = false
    private var fetchJob: Job? = null

    init {
        _state.update { it.copy(isLoading = true) }

        viewModelScope.launch {
            athletesUseCase().combine(_state.map { it.search }.distinctUntilChanged()) { athletes, query ->
                if (query.isBlank()) athletes
                else athletes.filter {
                    it.firstName.contains(query, ignoreCase = true) ||
                            it.lastName.contains(query, ignoreCase = true)
                }
            }.collect { filteredAthletes ->
                _state.update { it.copy(athletes = filteredAthletes, isLoading = false) }
            }
        }

        _state
            .map { it.search }
            .distinctUntilChanged()
            .debounce(1000)
            .onEach { resetAndFetch() }
            .launchIn(viewModelScope)
    }

    private fun resetAndFetch() {
        currentPage = 0
        isLastPage = false
        fetchJob?.cancel()
        _state.update { it.copy(errorMessage = null) }
        loadNextPage()
    }

    fun onSearchChanged(search: String) = _state.update { it.copy(search = search) }

    fun loadNextPage() {
        if (isLastPage || _state.value.isFetchingNextPage) return

        fetchJob = viewModelScope.launch {
            _state.update { it.copy(isFetchingNextPage = true, errorMessage = null) }

            val result = fetchAthletesUseCase(_state.value.search, currentPage)

            result.onSuccess { last ->
                isLastPage = last
                currentPage++
            }.onFailure { e ->
                _state.update { it.copy(errorMessage = e.message) }
            }

            _state.update { it.copy(isFetchingNextPage = false) }
        }
    }
}
