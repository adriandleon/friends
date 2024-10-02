package com.adriandeleon.friends.friends

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.adriandeleon.friends.MainActivity
import org.junit.Ignore
import org.junit.Rule
import org.junit.Test

class FriendsScreenTest {

    @get:Rule
    val rule = createAndroidComposeRule<MainActivity>()

    @Ignore("Test incomplete")
    @Test
    fun displaysFriendsScreen() {
        launchFriendsFor("email@email.com", rule) {
            tapOnFriends()
        } verify {
            friendsScreenIsPresent()
        }
    }
}