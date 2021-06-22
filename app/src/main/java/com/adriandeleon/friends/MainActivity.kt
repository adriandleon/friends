package com.adriandeleon.friends

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import com.adriandeleon.friends.signup.SignUp
import com.adriandeleon.friends.ui.theme.FriendsTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FriendsTheme {
                Surface(color = MaterialTheme.colors.background) {
                    SignUp()
                }
            }
        }
    }
}
