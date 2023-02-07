package com.adriandeleon.friends.timeline

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.adriandeleon.friends.MainActivity
import com.adriandeleon.friends.R
import com.adriandeleon.friends.signup.launchSignUpScreen

typealias MainActivityRule = AndroidComposeTestRule<ActivityScenarioRule<MainActivity>, MainActivity>

fun launchTimelineFor(
    email: String,
    password: String,
    testRule: MainActivityRule,
    block: TimelineRobot.() -> Unit
): TimelineRobot {
    launchSignUpScreen(testRule) {
        typeEmail(email)
        typePassword(password)
        submit()
    }
    return TimelineRobot(testRule).apply(block)
}

class TimelineRobot(
    private val testRule: MainActivityRule
) {
    infix fun verify(block: TimelineVerificationRobot.() -> Unit): TimelineVerificationRobot {
        return TimelineVerificationRobot(testRule).apply(block)
    }
}

class TimelineVerificationRobot(private val rule: MainActivityRule) {

    fun emptyTimelineMessageIsDisplayed() {
        val emptyTimelineMessage = rule.activity.getString(R.string.emptyTimelineMessage)
        rule.onNodeWithText(emptyTimelineMessage)
            .assertIsDisplayed()
    }
}
