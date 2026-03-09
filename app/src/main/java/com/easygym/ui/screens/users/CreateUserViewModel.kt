package com.easygym.ui.screens.users

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.easygym.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class CreateUserState(
    val nome: String = "",
    val cognome: String = "",
    val email: String = "",
    val ruolo: String? = "Athlete",
    val coachCertificazione: String = "",
    val athletePeso: String = "",
    val athleteAltezza: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

@HiltViewModel
class CreateUserViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _state = MutableStateFlow(CreateUserState())
    val state: StateFlow<CreateUserState> = _state.asStateFlow()

    fun onRuoloSelected(ruolo: String) {
        _state.update { it.copy(ruolo = ruolo) }
    }

    fun onNomeChange(nome: String) {
        _state.update { it.copy(nome = nome) }
    }

    fun onCognomeChange(cognome: String) {
        _state.update { it.copy(cognome = cognome) }
    }

    fun onEmailChange(email: String) {
        _state.update { it.copy(email = email) }
    }

    fun onCoachCertificazioneChange(text: String) {
        _state.update { it.copy(coachCertificazione = text) }
    }

    fun onAthletePesoChange(text: String) {
        _state.update { it.copy(athletePeso = text) }
    }

    fun onAthleteAltezzaChange(text: String) {
        _state.update { it.copy(athleteAltezza = text) }
    }

    fun createAccount() {
        val currentState = _state.value
        if (currentState.nome.isBlank() || currentState.cognome.isBlank() || currentState.email.isBlank() || currentState.ruolo.isNullOrBlank()) {
            _state.update { it.copy(errorMessage = "Compila tutti i campi") }
            return
        }

        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, errorMessage = null) }
            try {
//                // chiamata al repository per creare l'utente
//                userRepository.createUser(
//                    nome = currentState.nome,
//                    cognome = currentState.cognome,
//                    email = currentState.email,
//                    ruolo = currentState.ruolo
//                )

                _state.update { CreateUserState() } // reset dopo successo
            } catch (e: Exception) {
                _state.update { it.copy(isLoading = false, errorMessage = e.message) }
            }
        }
    }

}