package com.adriandeleon.friends.people

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.adriandeleon.friends.MainActivity
import com.adriandeleon.friends.R
import com.adriandeleon.friends.timeline.launchTimelineFor

private typealias MainActivityRule = AndroidComposeTestRule<ActivityScenarioRule<MainActivity>, MainActivity>

fun launchPeopleFor(
    email: String,
    rule: MainActivityRule,
    block: PeopleRobot.() -> Unit
): PeopleRobot {
    launchTimelineFor(email, "Pas$123", rule) {}
    return PeopleRobot(rule).apply(block)
}

class PeopleRobot(private val rule: MainActivityRule) {

    fun tapOnPeople() {
        val people = rule.activity.getString(R.string.people)
        rule.onNodeWithText(people)
            .performClick()
    }

    infix fun verify(block: PeopleVerificationRobot.() -> Unit): PeopleVerificationRobot {
        return PeopleVerificationRobot(rule).apply(block)
    }
}

class PeopleVerificationRobot(private val rule: MainActivityRule) {

    fun peopleScreenIsPresent() {
        val people = rule.activity.getString(R.string.people)
        rule.onNodeWithText(people)
            .assertIsDisplayed()
    }
}
