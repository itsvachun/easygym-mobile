package com.easygym.ui.screens.athletes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.easygym.domain.model.Athlete
import com.easygym.domain.usecase.AthletesUseCase
import com.easygym.domain.usecase.FetchAllAthletesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class AthletesState(
    val isLoading: Boolean = true,
    val athletes: List<Athlete> = listOf(),
    val errorMessage: String? = null
)

@HiltViewModel
class AthletesViewModel @Inject constructor(
    private val athletesUseCase: AthletesUseCase,
    private val fetchAllAthletesUseCase: FetchAllAthletesUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(AthletesState())
    val state: StateFlow<AthletesState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            _state.update { it.copy(errorMessage = fetchAllAthletesUseCase().exceptionOrNull()?.message) }
            athletesUseCase().collect { athletes ->
                _state.update { it.copy(isLoading = false, athletes = athletes) }
            }
        }
    }
}