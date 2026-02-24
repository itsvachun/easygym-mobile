package com.easygym.ui.screens

import android.util.Base64
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.easygym.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import org.json.JSONObject
import javax.inject.Inject

@HiltViewModel
class GateViewModel @Inject constructor(private val repository: AuthRepository) : ViewModel() {
    val state: StateFlow<Boolean> = repository.jwtToken
        .map { token ->
            isTokenValid(token)
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = false
        )

    private fun isTokenValid(token: String?): Boolean {
        if (token.isNullOrBlank()) return false

        return try {
            val parts = token.split(".")
            if (parts.size != 3) return false

            val payload = String(Base64.decode(parts[1], Base64.URL_SAFE))
            val json = JSONObject(payload)

            val exp = json.getLong("exp")
            val currentTime = System.currentTimeMillis() / 1000

            currentTime < exp
        } catch (e: Exception) {
            false
        }
    }
}