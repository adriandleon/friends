package com.adriandeleon.friends.signup

import android.content.res.Configuration.UI_MODE_TYPE_NORMAL
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.adriandeleon.friends.R
import com.adriandeleon.friends.ui.theme.FriendsTheme

@Composable
fun SignUp() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        var email by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }

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

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = { }
        ) {
            Text(text = stringResource(id = R.string.signUp))
        }
    }
}

@Composable
private fun ScreenTitle(@StringRes resourceId: Int) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(id = resourceId),
            style = typography.h4
        )
    }
}

@Composable
private fun EmailField(
    value: String,
    onValueChange: (String) -> Unit
) {
    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        value = value,
        label = {
            Text(text = stringResource(id = R.string.email))
        },
        onValueChange = onValueChange
    )
}

@Composable
private fun PasswordField(
    value: String,
    onValueChange: (String) -> Unit
) {
    var isVisible by remember { mutableStateOf(false) }

    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        value = value,
        trailingIcon = {
            IconButton(onClick = { isVisible = !isVisible }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_visibility),
                    contentDescription = stringResource(id = R.string.toggleVisibility)
                )
            }
        },
        visualTransformation = if (isVisible) {
            VisualTransformation.None
        } else {
            PasswordVisualTransformation()
        },
        label = {
            Text(text = stringResource(id = R.string.password))
        },
        onValueChange = onValueChange
    )
}

@Composable
@Preview(device = Devices.PIXEL_4, uiMode = UI_MODE_TYPE_NORMAL)
fun SignUpPreview() {
    FriendsTheme {
        SignUp()
    }
}