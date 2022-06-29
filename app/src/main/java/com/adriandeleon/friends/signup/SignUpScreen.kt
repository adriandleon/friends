package com.adriandeleon.friends.signup

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.adriandeleon.friends.R
import com.adriandeleon.friends.domain.user.InMemoryUserCatalog
import com.adriandeleon.friends.domain.user.UserRepository
import com.adriandeleon.friends.domain.validation.RegexCredentialsValidator
import com.adriandeleon.friends.signup.state.SignUpState

@Composable
fun SignUpScreen(onSignedUp: () -> Unit) {

    val credentialsValidator = RegexCredentialsValidator()
    val userRepository = UserRepository(InMemoryUserCatalog())
    val signUpViewModel = SignUpViewModel(credentialsValidator, userRepository)

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var about by remember { mutableStateOf("") }
    val signUpState by signUpViewModel.signUpState.observeAsState()

    if (signUpState is SignUpState.SignedUp) {
        onSignedUp()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        ScreenTitle(R.string.createAnAccount)
        Spacer(modifier = Modifier.height(16.dp))
        EmailField(
            value = email,
            onValueChange = { email = it }
        )
        PasswordField(
            value = password,
            onValueChange = { password = it }
        )
        AboutField(
            value = about,
            onValueChange = { about = it }
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                signUpViewModel.createAccount(email, password, about)
            },
        ) {
            Text(text = stringResource(id = R.string.signUp))
        }
    }
}

@Composable
private fun ScreenTitle(@StringRes resource: Int) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(id = resource),
            style = typography.h4
        )
    }
}

@Composable
private fun EmailField(
    value: String,
    onValueChange: (String) -> Unit,
) {
    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        value = value,
        label = { Text(text = stringResource(id = R.string.email)) },
        onValueChange = onValueChange
    )
}

@Composable
private fun PasswordField(
    value: String,
    onValueChange: (String) -> Unit,
) {
    var isVisible by remember { mutableStateOf(false) }
    val visualTransformation = if (isVisible) {
        VisualTransformation.None
    } else {
        PasswordVisualTransformation()
    }

    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .testTag(stringResource(id = R.string.password)),
        value = value,
        trailingIcon = {
            VisibilityToggle(isVisible) {
                isVisible = !isVisible
            }
        },
        visualTransformation = visualTransformation,
        label = { Text(text = stringResource(id = R.string.password)) },
        onValueChange = onValueChange
    )
}

@Composable
private fun VisibilityToggle(
    isVisible: Boolean,
    onToggle: () -> Unit,
) {
    IconButton(
        onClick = onToggle
    ) {
        val resource = if (isVisible) R.drawable.ic_invisible else R.drawable.ic_visible
        Icon(
            painter = painterResource(id = resource),
            contentDescription = stringResource(id = R.string.toggleVisibility)
        )
    }
}

@Composable
fun AboutField(
    value: String,
    onValueChange: (String) -> Unit,
) {
    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        value = value,
        label = { Text(text = stringResource(id = R.string.about)) },
        onValueChange = onValueChange
    )
}
