package com.easygym.ui.screens.changepassword

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.easygym.ui.components.PasswordTextField
import com.easygym.ui.components.PrimaryButton
import com.easygym.ui.theme.LocalColors

@Composable
fun ChangePasswordScreen(
    onSaved: () -> Unit = {},
    onBack: () -> Unit = {},
    modifier: Modifier = Modifier,
) {
    var current by remember { mutableStateOf("") }
    var newPwd by remember { mutableStateOf("") }
    var confirm by remember { mutableStateOf("") }

    Scaffold { innerPadding ->
        Column(
            verticalArrangement = Arrangement.Center,
            modifier = modifier
                .fillMaxSize()
                .background(LocalColors.current.bg)
                .padding(innerPadding)
                .padding(horizontal = 18.dp)
                .verticalScroll(rememberScrollState()),
        ) {
//        BackButton(label = "Profilo", onClick = onBack)
            Text(
                "Cambio password",
                style = MaterialTheme.typography.displayMedium,
                color = LocalColors.current.text
            )
            Spacer(Modifier.height(8.dp))
            Text(
                "Inserisci la password attuale per procedere",
                style = MaterialTheme.typography.labelLarge,
                color = LocalColors.current.gray
            )
            Spacer(Modifier.height(50.dp))
            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                PasswordTextField(
                    label = "Password attuale",
                    value = current,
                    visualTransformation = PasswordVisualTransformation(),
                    onValueChange = { current = it },
                )
                Spacer(Modifier.height(12.dp))
                PasswordTextField(
                    label = "Nuova password",
                    value = newPwd,
                    visualTransformation = PasswordVisualTransformation(),
                    onValueChange = { newPwd = it },
                )
                Spacer(Modifier.height(12.dp))
                PasswordTextField(
                    label = "Conferma nuova password",
                    value = confirm,
                    visualTransformation = PasswordVisualTransformation(),
                    onValueChange = { confirm = it },
                )
            }
            Spacer(Modifier.height(70.dp))
            PrimaryButton(text = "Salva nuova password", onClick = onSaved)
        }
    }
}

