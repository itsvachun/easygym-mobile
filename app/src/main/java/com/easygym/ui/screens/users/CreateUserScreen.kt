package com.easygym.ui.screens.users

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.easygym.ui.components.EasyGymTextField
import com.easygym.ui.components.EmailTextField
import com.easygym.ui.components.PrimaryButton
import com.easygym.ui.components.SecondaryButton
import java.time.LocalDate

@Composable
fun CreateUserScreen(
    modifier: Modifier = Modifier,
    viewModel: CreateUserViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit
) {

    val state by viewModel.state.collectAsState()
    val scrollState = rememberScrollState()

    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
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
                color = MaterialTheme.colorScheme.primary,
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
                        if (state.ruolo == "Athlete") {
                            PrimaryButton(text = "Atleta", onClick = {})
                        } else {
                            SecondaryButton(
                                text = "Atleta",
                                onClick = { viewModel.onRuoloSelected("Athlete") }
                            )
                        }
                    }

                    Box(modifier = Modifier.weight(1f)) {
                        if (state.ruolo == "Coach") {
                            PrimaryButton(text = "Coach", onClick = {})
                        } else {
                            SecondaryButton(
                                text = "Coach",
                                onClick = { viewModel.onRuoloSelected("Coach") }
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
                            value = state.firstName,
                            label = "Nome",
                            placeholder = "Mario",
                            onValueChange = { viewModel.onNomeChange(it) }
                        )
                    }

                    Box(modifier = Modifier.weight(1f)) {
                        EasyGymTextField(
                            value = state.lastName,
                            label = "Cognome",
                            placeholder = "Rossi",
                            onValueChange = { viewModel.onCognomeChange(it) }
                        )
                    }
                }

                EmailTextField(
                    value = state.email,
                    onValueChange = { viewModel.onEmailChange(it) }
                )

                EasyGymTextField(
                    value = state.phone,
                    label = "Telefono",
                    placeholder = "+39 3331234567",
                    onValueChange = { viewModel.onPhoneChange(it) }
                )

                EasyGymTextField(
                    value = state.password,
                    label = "Password",
                    placeholder = "P4ssw0rd!",
                    onValueChange = { viewModel.onPasswordChange(it) }
                )

                EasyGymTextField(
                    value = state.groupId,
                    label = "Gruppo",
                    placeholder = "ID Gruppo",
                    onValueChange = { viewModel.onGroupIdChange(it) }
                )

                when (state.ruolo) {

                    "Coach" -> {

                        EasyGymTextField(
                            value = state.bio,
                            label = "Bio Coach",
                            placeholder = "Esperienza, certificazioni...",
                            onValueChange = { viewModel.onBioChange(it) }
                        )

                    }

                    "Athlete" -> {

                        EasyGymTextField(
                            value = state.birthDate?.toString() ?: "",
                            label = "Data di nascita",
                            placeholder = "YYYY-MM-DD",
                            onValueChange = {
                                runCatching { LocalDate.parse(it) }
                                    .onSuccess { date -> viewModel.onBirthDateChange(date) }
                            }
                        )

                        EasyGymTextField(
                            value = state.taxCode,
                            label = "Codice fiscale",
                            placeholder = "RSSMRA90A01H501X",
                            onValueChange = { viewModel.onTaxCodeChange(it) }
                        )

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            Box(modifier = Modifier.weight(2f)) {
                                EasyGymTextField(
                                    value = state.address,
                                    label = "Indirizzo",
                                    placeholder = "Via Roma 10",
                                    onValueChange = { viewModel.onAddressChange(it) }
                                )
                            }

                            Box(modifier = Modifier.weight(1f)) {
                                EasyGymTextField(
                                    value = state.city,
                                    label = "Città",
                                    placeholder = "Torino",
                                    onValueChange = { viewModel.onCityChange(it) }
                                )
                            }

                            Box(modifier = Modifier.weight(1f)) {
                                EasyGymTextField(
                                    value = state.postalCode,
                                    label = "CAP",
                                    placeholder = "10100",
                                    onValueChange = { viewModel.onPostalCodeChange(it) }
                                )
                            }
                        }

                        EasyGymTextField(
                            value = state.medicalExpDate?.toString() ?: "",
                            label = "Scadenza certificato medico",
                            placeholder = "YYYY-MM-DD",
                            onValueChange = {
                                runCatching { LocalDate.parse(it) }
                                    .onSuccess { date -> viewModel.onMedicalExpDateChange(date) }
                            }
                        )

                        EasyGymTextField(
                            value = state.notes,
                            label = "Note",
                            placeholder = "Note atleta",
                            onValueChange = { viewModel.onNotesChange(it) }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(5.dp))

                PrimaryButton(
                    text = "Crea Account",
                    onClick = { viewModel.createAccount() }
                )

                state.errorMessage?.let {
                    Text(it, color = MaterialTheme.colorScheme.error)
                }
            }
        }
    }
}