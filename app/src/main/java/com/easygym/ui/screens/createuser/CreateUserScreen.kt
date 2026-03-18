package com.easygym.ui.screens.createuser

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.easygym.ui.components.*
import com.easygym.ui.theme.LocalColors

@Composable
fun CreateUserScreen(
    viewModel: CreateUserViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit
) {

    val state by viewModel.state.collectAsState()
    val scrollState = rememberScrollState()

    Scaffold { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 24.dp),
            contentAlignment = Alignment.Center
        ) {

            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.Start,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    text = "< Utenti",
                    style = MaterialTheme.typography.bodyMedium,
                    color = LocalColors.current.red,
                    modifier = Modifier.clickable { onNavigateBack() }
                )

                Column(
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxWidth()
                        .verticalScroll(scrollState)
                ) {

                    Text(
                        text = "Crea Utente",
                        style = MaterialTheme.typography.headlineLarge
                    )

                    Text(
                        text = "RUOLO",
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {

                        Box(modifier = Modifier.weight(1f)) {
                            if (state.createUser is CreateUser.Athlete) {
                                PrimaryButton(text = "Atleta", onClick = {})
                            } else {
                                SecondaryButton(
                                    text = "Atleta",
                                    onClick = { viewModel.onRoleSelected(CreateUser.Athlete()) }
                                )
                            }
                        }

                        Box(modifier = Modifier.weight(1f)) {
                            if (state.createUser is CreateUser.Coach) {
                                PrimaryButton(text = "Coach", onClick = {})
                            } else {
                                SecondaryButton(
                                    text = "Coach",
                                    onClick = { viewModel.onRoleSelected(CreateUser.Coach()) }
                                )
                            }
                        }

                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Box(modifier = Modifier.weight(1f)) {
                            EasyGymTextField(
                                value = state.createUser.firstName,
                                label = "Nome",
                                placeholder = "Mario",
                                onValueChange = { viewModel.updateState(firstName = it) }
                            )
                        }

                        Box(modifier = Modifier.weight(1f)) {
                            EasyGymTextField(
                                value = state.createUser.lastname,
                                label = "Cognome",
                                placeholder = "Rossi",
                                onValueChange = { viewModel.updateState(lastName = it) }
                            )
                        }
                    }

                    EmailTextField(
                        value = state.createUser.email,
                        onValueChange = { viewModel.updateState(email = it) }
                    )

                    EasyGymTextField(
                        value = state.createUser.phone,
                        label = "Telefono",
                        placeholder = "+39 3331234567",
                        onValueChange = { viewModel.updateState(phone = it) }
                    )

                    EasyGymTextField(
                        value = state.createUser.password,
                        label = "Password",
                        placeholder = "P4ssw0rd!",
                        onValueChange = { viewModel.updateState(password = it) }
                    )

                    /*EasyGymTextField(
                        value = state.groupId,
                        label = "Gruppo",
                        placeholder = "ID Gruppo",
                        onValueChange = { viewModel.onGroupIdChange(it) }
                    )*/

                    when (val user = state.createUser) {
                        is CreateUser.Coach -> {
                            EasyGymTextField(
                                value = user.bio,
                                label = "Bio Coach",
                                placeholder = "Esperienza, certificazioni...",
                                onValueChange = { viewModel.updateState(bio = it) }
                            )
                        }

                        is CreateUser.Athlete -> {

                            EasyGymDatePicker(
                                value = user.birthDate,
                                label = "Data di nascita",
                                onDateSelected = {
                                    println(it)
                                    viewModel.updateState(birthDate = it)
                                }
                            )

                            EasyGymTextField(
                                value = user.taxCode,
                                label = "Codice fiscale",
                                placeholder = "RSSMRA90A01H501X",
                                onValueChange = { viewModel.updateState(taxCode = it) }
                            )

                            EasyGymTextField(
                                value = user.address,
                                label = "Indirizzo",
                                placeholder = "Via Roma 10",
                                onValueChange = { viewModel.updateState(address = it) }
                            )

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(16.dp)
                            ) {

                                Box(modifier = Modifier.weight(1f)) {
                                    EasyGymTextField(
                                        value = user.city,
                                        label = "Città",
                                        placeholder = "Torino",
                                        onValueChange = { viewModel.updateState(city = it) }
                                    )
                                }

                                Box(modifier = Modifier.weight(1f)) {
                                    EasyGymTextField(
                                        value = user.postalCode,
                                        label = "CAP",
                                        placeholder = "10100",
                                        onValueChange = { viewModel.updateState(postalCode = it) }
                                    )
                                }
                            }

                            EasyGymDatePicker(
                                value = user.medicalExpDate,
                                label = "Scadenza certificato medico",
                                onDateSelected = {
                                    println(it)
                                    viewModel.updateState(medicalExpDate = it)
                                }
                            )

                            EasyGymTextField(
                                value = user.notes,
                                label = "Note",
                                placeholder = "Note atleta",
                                onValueChange = { viewModel.updateState(notes = it) }
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(5.dp))

                    PrimaryButton(
                        text = "Crea Account",
                        onClick = { viewModel.createAccount() }
                    )

                    state.errorMessage?.let {
                        Text(it, color = LocalColors.current.red)
                    }
                }
            }
        }
    }
}