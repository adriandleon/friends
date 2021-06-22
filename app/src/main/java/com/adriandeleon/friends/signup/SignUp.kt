package com.adriandeleon.friends.signup

import android.content.res.Configuration.UI_MODE_TYPE_NORMAL
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
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

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(id = R.string.createAnAccount),
                style = typography.h4
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = "",
            label = {
                Text(text = stringResource(id = R.string.email))
            },
            onValueChange = {}
        )

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = "",
            label = {
                Text(text = stringResource(id = R.string.password))
            },
            onValueChange = {}
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
@Preview(device = Devices.PIXEL_4, uiMode = UI_MODE_TYPE_NORMAL)
fun SignUpPreview() {
    FriendsTheme {
        SignUp()
    }
}