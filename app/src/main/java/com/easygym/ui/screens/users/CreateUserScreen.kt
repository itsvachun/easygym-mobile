package com.easygym.ui.screens.users

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.easygym.ui.components.*


@Composable
fun CreateUserScreen(
    modifier: Modifier = Modifier
) {
    var nome by remember { mutableStateOf("") }
    var cognome by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }

    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "< Utenti",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.fillMaxWidth()
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
                    IconButton(
                        text = "Coach",
                        icon = Icons.Default.Add,
                        onClick = { }
                    )
                }

                Box(modifier = Modifier.weight(1f)) {
                    IconButton(
                        text = "Athlete",
                        icon = Icons.Default.Add,
                        onClick = { }
                    )
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Box(modifier = Modifier.weight(1f)) {
                    EasyGymTextField(
                        value = nome,
                        label = "Nome",
                        placeholder = "Mario",
                        onValueChange = {},
                    )
                }

                Box(modifier = Modifier.weight(1f)) {
                    EasyGymTextField(
                        value = cognome,
                        label = "Cognome",
                        placeholder = "Rossi",
                        onValueChange = {}
                    )
                }

            }

            EmailTextField(
                value = email,
                onValueChange = {}

            )


            Spacer(modifier = Modifier.height(24.dp))

            PrimaryButton(
                text = "Crea Account",
                onClick = {})
            SecondaryButton(
                text = "Elimina Account",
                onClick = {}
            )

        }
    }
}

@Preview(showBackground = true)
@Composable
fun CreateUserScreenPreview() {
    MaterialTheme {
        CreateUserScreen()
    }
}