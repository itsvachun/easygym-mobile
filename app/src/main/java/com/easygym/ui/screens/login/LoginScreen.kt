package com.easygym.ui.screens.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.easygym.R
import com.easygym.ui.components.EmailTextField
import com.easygym.ui.components.PasswordTextField
import com.easygym.ui.components.PrimaryButton
import com.easygym.ui.theme.LocalColors


@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    viewModel: LoginViewModel = hiltViewModel(),
) {
    val loginState by viewModel.state.collectAsState()

    Scaffold { innerPadding ->
        Box(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 24.dp),
            contentAlignment = Alignment.Center
        ) {

            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "Logo EasyGym",
                    modifier = Modifier
                        .size(120.dp)
                        .padding(bottom = 16.dp),
                    contentScale = ContentScale.Fit
                )

                Text(
                    text = "Login",
                    style = MaterialTheme.typography.headlineLarge
                )

                EmailTextField(
                    value = loginState.loginRequest.email,
                    onValueChange = viewModel::onEmailChanged,
                )

                PasswordTextField(
                    value = loginState.loginRequest.password,
                    onValueChange = viewModel::onPasswordChanged,
                    label = "Password",
                    visualTransformation = if (loginState.isPasswordVisible) VisualTransformation.None
                    else PasswordVisualTransformation(),
                    trailingIcon = {
                        IconButton(onClick = viewModel::togglePasswordVisibility) {
                            Icon(
                                imageVector = if (loginState.isPasswordVisible) Icons.Default.Visibility
                                else Icons.Default.VisibilityOff,
                                contentDescription = null
                            )
                        }
                    },
                )

                loginState.errorMessage?.let {
                    Text(
                        text = it,
                        color = LocalColors.current.red,
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                PrimaryButton(
                    text = "Accedi",
                    onClick = viewModel::login
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    MaterialTheme {
        LoginScreen()
    }
}