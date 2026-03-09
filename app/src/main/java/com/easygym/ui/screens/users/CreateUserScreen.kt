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


@Composable
fun CreateUserScreen(
    viewModel: CreateUserViewModel = hiltViewModel(),
    modifier: Modifier = Modifier,
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
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(scrollState),
        ) {

            Text(
                text = "< Utenti",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onNavigateBack() }
            )

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
                    if (state.ruolo == "Coach") {
                        PrimaryButton(
                            text = "Coach",
                            onClick = {}
                        )
                    } else {
                        SecondaryButton(
                            text = "Coach",
                            onClick = { viewModel.onRuoloSelected("Coach") }
                        )
                    }
                }

                // Bottone Athlete
                Box(modifier = Modifier.weight(1f)) {
                    if (state.ruolo == "Athlete") {
                        PrimaryButton(
                            text = "Atleta",
                            onClick = {}
                        )
                    } else {
                        SecondaryButton(
                            text = "Atleta",
                            onClick = { viewModel.onRuoloSelected("Athlete") }
                        )
                    }
                }
            }

            EasyGymTextField(
                value = state.nome,
                label = "Nome",
                placeholder = "Mario",
                onValueChange = { viewModel.onNomeChange(it) }
            )
            EasyGymTextField(
                value = state.cognome,
                label = "Cognome",
                placeholder = "Rossi",
                onValueChange = { viewModel.onCognomeChange(it) }
            )
            EmailTextField(
                value = state.email,
                onValueChange = { viewModel.onEmailChange(it) }
            )

            when (state.ruolo) {
                "Coach" -> {
                    EasyGymTextField(
                        value = state.coachCertificazione,
                        label = "Certificazione Coach",
                        placeholder = "certificato",
                        onValueChange = { viewModel.onCoachCertificazioneChange(it) }
                    )
                }

                "Athlete" -> {
                    EasyGymTextField(
                        value = state.athletePeso,
                        label = "Peso (kg)",
                        placeholder = "80kg",
                        onValueChange = { viewModel.onAthletePesoChange(it) }
                    )
                    EasyGymTextField(
                        value = state.athleteAltezza,
                        label = "Altezza (cm)",
                        placeholder = "180cm",
                        onValueChange = { viewModel.onAthleteAltezzaChange(it) }
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            PrimaryButton(
                text = "Crea Account",
                onClick = { viewModel.createAccount() }
            )



            state.errorMessage?.let { Text(it, color = MaterialTheme.colorScheme.error) }

        }
    }
}
/*
@Preview(showBackground = true)
@Composable
fun CreateUserScreenPreview() {
    MaterialTheme {
        CreateUserScreen(nav)
    }
}*/