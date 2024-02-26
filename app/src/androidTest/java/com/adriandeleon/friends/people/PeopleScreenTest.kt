package com.adriandeleon.friends.people

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.adriandeleon.friends.MainActivity
import org.junit.Ignore
import org.junit.Rule
import org.junit.Test

class PeopleScreenTest {

    @get:Rule
    val rule = createAndroidComposeRule<MainActivity>()

    @Test
    @Ignore("In development")
    fun displaysPeople() {
        launchPeopleFor("email@email.com", rule) {
            tapOnPeople()
        } verify {
            peopleScreenIsPresent()
        }
    }
}