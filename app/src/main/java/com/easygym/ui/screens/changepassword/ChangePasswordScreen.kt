package com.easygym.ui.screens.changepassword

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.easygym.ui.components.PasswordTextField
import com.easygym.ui.components.PrimaryButton
import com.easygym.ui.theme.LocalColors

@Composable
fun ChangePasswordScreen(
    viewModel: ChangePasswordViewModel = hiltViewModel(),
    modifier: Modifier = Modifier,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

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
            state.errorMessage?.let { message ->
                Text("Errore durante il cambio password: $message")
            }
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
                    value = state.updatePassword.oldPassword,
                    visualTransformation = PasswordVisualTransformation(),
                    onValueChange = { viewModel.updateState(oldPassword = it) },
                )
                Spacer(Modifier.height(12.dp))
                PasswordTextField(
                    label = "Nuova password",
                    value = state.updatePassword.newPassword,
                    visualTransformation = PasswordVisualTransformation(),
                    onValueChange = { viewModel.updateState(newPassword = it) },
                )
                Spacer(Modifier.height(12.dp))
                PasswordTextField(
                    label = "Conferma nuova password",
                    value = state.confirmNewPassword,
                    visualTransformation = PasswordVisualTransformation(),
                    onValueChange = { viewModel.updateState(confirmNewPassword = it) },
                )
            }
            Spacer(Modifier.height(70.dp))
            PrimaryButton(text = "Salva nuova password", onClick = viewModel::changePassword)
        }
    }
}

