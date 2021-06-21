package com.adriandeleon.friends.signup

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.adriandeleon.friends.MainActivity
import org.junit.Rule
import org.junit.Test

class SignUpTest {

    @get:Rule
    val signUpTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun performSignUP() {
        launchSignUpScreen(signUpTestRule) {
            typeEmail("someone@friends.com")
            typePassword("password")
            submit()
        } verify {
            timelineScreenIsPresent()
        }
    }
}