package com.adriandeleon.friends.timeline

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.adriandeleon.friends.R

@Composable
fun Timeline() {
    Text(text = stringResource(id = R.string.timeline))
}