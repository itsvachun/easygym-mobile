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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 24.dp)
                .padding(vertical = 10.dp)
                .verticalScroll(scrollState),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.Start
        ) {

            Text(
                text = "< Utenti",
                style = MaterialTheme.typography.bodyMedium,
                color = LocalColors.current.red,
                modifier = Modifier.clickable { onNavigateBack() }
            )

            Text(
                text = "Crea Utente",
                style = MaterialTheme.typography.headlineLarge
            )

            Spacer(modifier = Modifier.height(4.dp))

            Column(
                verticalArrangement = Arrangement.spacedBy(10.dp),
            ) {
                Text(
                    text = "TIPOLOGIA",
                    style = MaterialTheme.typography.labelLarge,
                    color = LocalColors.current.surface4,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 2.sp
                )

                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    EasyGymSegmentedButton(
                        modifier = Modifier.weight(1f),
                        text = "Atleta",
                        icon = "\uD83C\uDFCB\uFE0F",
                        borderColor = LocalColors.current.purple.takeIf { state.isAthlete },
                        textColor = LocalColors.current.purple.takeIf { state.isAthlete },
                        backgroundColor = LocalColors.current.purpleDim.takeIf { state.isAthlete },
                        onClick = { viewModel.onRoleSelected(CreateUser.Athlete()) },
                    )

                    EasyGymSegmentedButton(
                        modifier = Modifier.weight(1f),
                        text = "Coach",
                        icon = "\uD83E\uDD4B",
                        borderColor = LocalColors.current.blue.takeIf { state.isCoach },
                        textColor = LocalColors.current.blue.takeIf { state.isCoach },
                        backgroundColor = LocalColors.current.blueDim.takeIf { state.isCoach },
                        onClick = { viewModel.onRoleSelected(CreateUser.Coach()) },
                    )
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
                        value = state.createUser.lastName,
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

            EasyGymDropDownMenu(
                items = state.groups,
                selectedItem = state.groups.find { it.id == state.groupId },
                onItemSelected = { group -> viewModel.onGroupSelected(group) },
                label = "Gruppo",
                itemLabel = { it.name }
            )

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
                        onDateSelected = { viewModel.updateState(birthDate = it) }
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
                        onDateSelected = { viewModel.updateState(medicalExpDate = it) }
                    )

                    EasyGymTextField(
                        value = user.notes,
                        label = "Note",
                        placeholder = "Note atleta",
                        onValueChange = { viewModel.updateState(notes = it) }
                    )
                }
            }

            Spacer(modifier = Modifier.height(6.dp))

            PrimaryButton(
                text = "Crea Account",
                onClick = viewModel::createUser
            )

            state.errorMessage?.let {
                Text(it, color = LocalColors.current.red)
            }

            Spacer(modifier = Modifier.height(6.dp))
        }
    }
}