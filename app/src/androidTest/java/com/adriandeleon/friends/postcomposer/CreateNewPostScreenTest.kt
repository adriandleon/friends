package com.adriandeleon.friends.postcomposer

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.adriandeleon.friends.MainActivity
import org.junit.Rule
import org.junit.Test

class CreateNewPostScreenTest {

    @get:Rule
    val createNewPostRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun createNewPost() {
        launchPostComposerFor("user@friends.com", createNewPostRule) {
            typePost("My New Post")
            submit()
        } verify {
            newlyCreatedPostIsShown("userId", "30-10-2021 15:30", "My New Post")
        }
    }
}

