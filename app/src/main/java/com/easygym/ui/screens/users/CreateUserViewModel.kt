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
import java.time.LocalDate
import javax.inject.Inject

data class CreateUserState(
    // valori in comune
    val ruolo: String? = "Athlete",
    val firstName: String = "",
    val lastName: String = "",
    val email: String = "",
    val phone: String = "",
    val password: String = "",
    val groupId: String = "",

    // solo coach
    val bio: String = "",

    // solo atleta
    val birthDate: LocalDate? = null,
    val taxCode: String = "",
    val address: String = "",
    val city: String = "",
    val postalCode: String = "",
    val medicalExpDate: LocalDate? = null,
    val notes: String = "",

    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

@HiltViewModel
open class CreateUserViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _state = MutableStateFlow(CreateUserState())
    val state: StateFlow<CreateUserState> = _state.asStateFlow()

    fun onRuoloSelected(ruolo: String) {
        _state.update { it.copy(ruolo = ruolo) }
    }

    fun onNomeChange(nome: String) {
        _state.update { it.copy(firstName = nome) }
    }

    fun onCognomeChange(cognome: String) {
        _state.update { it.copy(lastName = cognome) }
    }

    fun onEmailChange(email: String) {
        _state.update { it.copy(email = email) }
    }

    fun onPhoneChange(phone: String) {
        _state.update { it.copy(phone = phone) }
    }

    fun onPasswordChange(password: String) {
        _state.update { it.copy(password = password) }
    }

    fun onGroupIdChange(groupId: String) {
        _state.update { it.copy(groupId = groupId) }
    }

    fun onBioChange(bio: String) {
        _state.update { it.copy(bio = bio) }
    }

    fun onBirthDateChange(birthDate: LocalDate) {
        _state.update { it.copy(birthDate = birthDate) }
    }

    fun onTaxCodeChange(taxCode: String) {
        _state.update { it.copy(taxCode = taxCode) }
    }

    fun onAddressChange(address: String) {
        _state.update { it.copy(address = address) }
    }

    fun onCityChange(city: String) {
        _state.update { it.copy(city = city) }
    }

    fun onPostalCodeChange(postalCode: String) {
        _state.update { it.copy(postalCode = postalCode) }
    }

    fun onMedicalExpDateChange(date: LocalDate) {
        _state.update { it.copy(medicalExpDate = date) }
    }

    fun onNotesChange(notes: String) {
        _state.update { it.copy(notes = notes) }
    }

    fun createAccount() {
        val currentState = _state.value

        if (
            currentState.firstName.isBlank() ||
            currentState.lastName.isBlank() ||
            currentState.email.isBlank() ||
            currentState.ruolo.isNullOrBlank()
        ) {
            _state.update { it.copy(errorMessage = "Compila tutti i campi obbligatori") }
            return
        }

        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, errorMessage = null) }

            try {

                // userRepository.createUser(...)

                _state.value = CreateUserState()

            } catch (e: Exception) {
                _state.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = e.message
                    )
                }
            }
        }
    }
}