package com.easygym.ui.navigation

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class BottomBarViewModel : ViewModel() {

    private val _state = MutableStateFlow(0)
    val state: StateFlow<Int> = _state.asStateFlow()

    fun updateSelectedIndex(index: Int) = _state.update { index }
}