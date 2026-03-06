package com.easygym.ui.screens.users

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.easygym.ui.components.EasyGymFAB
import com.easygym.ui.components.EasyGymTextField
import com.easygym.ui.components.PrimaryButton
import com.easygym.ui.components.SecondaryButton

@Composable
fun UserScreen(
    modifier: Modifier = Modifier
) {

    var ricerca by remember { mutableStateOf("") }
    var selectedRole by remember { mutableStateOf("Tutti") }

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
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(150.dp)
            )
            {
                Text(
                    text = "Utenti",
                    style = MaterialTheme.typography.headlineLarge
                )

                EasyGymFAB(
                    hasShadow = false,
                    onClick = { },

                    )
            }
            EasyGymTextField(
                value = ricerca,
                label = "",
                placeholder = "Cerca per nome",
                onValueChange = {}
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {

                Box(modifier = Modifier.weight(1f)) {
                    if (selectedRole == "Tutti") {
                        PrimaryButton(
                            text = "Tutti",
                            onClick = { }
                        )
                    } else {
                        SecondaryButton(
                            text = "Tutti",
                            onClick = { selectedRole = "Tutti" }
                        )
                    }
                }

                Box(modifier = Modifier.weight(1f)) {
                    if (selectedRole == "Coach") {
                        PrimaryButton(
                            text = "Coach",
                            onClick = { }
                        )
                    } else {
                        SecondaryButton(
                            text = "Coach",
                            onClick = { selectedRole = "Coach" }
                        )
                    }
                }

                Box(modifier = Modifier.weight(1f)) {
                    if (selectedRole == "Atleti") {
                        PrimaryButton(
                            text = "Atleti",
                            onClick = { }
                        )
                    } else {
                        SecondaryButton(
                            text = "Atleti",
                            onClick = { selectedRole = "Atleti" }
                        )
                    }
                }
            }
        }
    }
}