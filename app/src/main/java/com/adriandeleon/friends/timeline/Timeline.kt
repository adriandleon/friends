package com.adriandeleon.friends.timeline

import android.content.res.Configuration
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import com.adriandeleon.friends.R
import com.adriandeleon.friends.ui.theme.FriendsTheme

@Composable
fun Timeline() {
    Text(text = stringResource(id = R.string.timeline))
}

@Composable
@Preview(device = Devices.PIXEL_4, uiMode = Configuration.UI_MODE_TYPE_NORMAL)
fun TimelinePreview() {
    FriendsTheme {
        Timeline()
    }
}

