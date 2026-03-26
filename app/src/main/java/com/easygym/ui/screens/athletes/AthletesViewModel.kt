package com.easygym.ui.screens.athletes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.easygym.domain.model.Athlete
import com.easygym.domain.usecase.athlete.AthletesUseCase
import com.easygym.domain.usecase.athlete.FetchAthletesUseCase
import com.easygym.ui.utils.PaginationHandler
import com.easygym.ui.utils.PaginationState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class AthletesState(
    val pagination: PaginationState<Athlete> = PaginationState()
)

@OptIn(FlowPreview::class)
@HiltViewModel
class AthletesViewModel @Inject constructor(
    private val athletesUseCase: AthletesUseCase,
    private val fetchAthletesUseCase: FetchAthletesUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(AthletesState())
    val state: StateFlow<AthletesState> = _state.asStateFlow()

    private val paginationHandler = PaginationHandler(
        scope = viewModelScope,
        fetchItems = fetchAthletesUseCase::invoke,
        localItemsFlow = athletesUseCase(),
        onStateUpdate = { pagination ->
            _state.update { it.copy(pagination = pagination) }
        }
    )

    fun onSearchChanged(search: String) = paginationHandler.onSearchChanged(search)

    fun loadNextPage() = paginationHandler.loadNextPage()
}
