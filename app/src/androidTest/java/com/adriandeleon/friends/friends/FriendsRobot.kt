package com.adriandeleon.friends.friends

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.adriandeleon.friends.MainActivity
import com.adriandeleon.friends.R
import com.adriandeleon.friends.timeline.launchTimelineFor

private typealias MainActivityRule = AndroidComposeTestRule<ActivityScenarioRule<MainActivity>, MainActivity>

fun launchFriendsFor(
    email: String,
    rule: MainActivityRule,
    block: FriendsRobot.() -> Unit
): FriendsRobot {
    launchTimelineFor(email, "Pas$123", rule) {}
    return FriendsRobot(rule).apply(block)
}

class FriendsRobot(private val rule: MainActivityRule) {

    fun tapOnFriends() {
        val friends = rule.activity.getString(R.string.friends)
        rule.onNodeWithText(friends)
            .performClick()
    }

    infix fun verify(block: FriendsVerificationRobot.() -> Unit): FriendsVerificationRobot {
        return FriendsVerificationRobot(rule).apply(block)
    }
}

class FriendsVerificationRobot(private val rule: MainActivityRule) {

    fun friendsScreenIsPresent() {
        val friends = rule.activity.getString(R.string.friends)
        rule.onAllNodesWithText(friends)
            .onFirst()
            .assertIsDisplayed()
    }
}
