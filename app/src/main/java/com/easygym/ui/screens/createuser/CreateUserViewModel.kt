package com.easygym.ui.screens.createuser

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.easygym.domain.repository.UserRepository
import com.easygym.domain.usecase.user.CreateUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

data class CreateUserState(
    val createUser: CreateUser = CreateUser.Athlete(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val groupId: String? = null
)

@HiltViewModel
open class CreateUserViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val createUserUseCase: CreateUserUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(CreateUserState())
    val state: StateFlow<CreateUserState> = _state.asStateFlow()

    fun onRoleSelected(user: CreateUser) = _state.update { currentState ->
        currentState.copy(
            createUser = when (user) {
                is CreateUser.Coach -> CreateUser.Coach()
                is CreateUser.Athlete -> CreateUser.Athlete()
            }
        )
    }

    fun updateState(
        firstName: String? = null,
        lastName: String? = null,
        email: String? = null,
        password: String? = null,
        groupId: String? = null,
        phone: String? = null,
        birthDate: LocalDate? = null,
        taxCode: String? = null,
        address: String? = null,
        city: String? = null,
        postalCode: String? = null,
        medicalExpDate: LocalDate? = null,
        notes: String? = null,
        bio: String? = null
    ) =
        _state.update { currentState ->
            currentState.copy(
                groupId = groupId,
                createUser = when (currentState.createUser) {
                    is CreateUser.Coach -> currentState.createUser.copy(
                        firstName = firstName ?: currentState.createUser.firstName,
                        lastName = lastName ?: currentState.createUser.lastName,
                        email = email ?: currentState.createUser.email,
                        password = password ?: currentState.createUser.password,
                        phone = phone ?: currentState.createUser.phone,
                        bio = bio ?: currentState.createUser.bio
                    )

                    is CreateUser.Athlete -> currentState.createUser.copy(
                        firstName = firstName ?: currentState.createUser.firstName,
                        lastName = lastName ?: currentState.createUser.lastName,
                        email = email ?: currentState.createUser.email,
                        password = password ?: currentState.createUser.password,
                        phone = phone ?: currentState.createUser.phone,
                        birthDate = birthDate ?: currentState.createUser.birthDate,
                        taxCode = taxCode ?: currentState.createUser.taxCode,
                        address = address ?: currentState.createUser.address,
                        city = city ?: currentState.createUser.city,
                        postalCode = postalCode ?: currentState.createUser.postalCode,
                        medicalExpDate = medicalExpDate ?: currentState.createUser.medicalExpDate,
                        notes = notes ?: currentState.createUser.notes
                    )
                }
            )
        }

    fun createUser() {
        val currentState = _state.value

        val currentUser = currentState.createUser
        if (currentUser.firstName.isBlank() || currentUser.lastName.isBlank() || currentUser.email.isBlank()
        ) {
            _state.update { it.copy(errorMessage = "Compila tutti i campi obbligatori") }
            return
        }


        viewModelScope.launch {
            println(currentState.groupId)
            try {
                createUserUseCase(user = currentState.createUser, currentState.groupId!!)
                _state.update { it.copy(isLoading = true, errorMessage = null) }
            } catch (e: Exception) {
                println(e)
            }
        }
    }
}