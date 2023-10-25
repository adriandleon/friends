package com.adriandeleon.friends.postcomposer

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.adriandeleon.friends.MainActivity
import com.adriandeleon.friends.R
import com.adriandeleon.friends.timeline.launchTimelineFor

private typealias MainActivityRule = AndroidComposeTestRule<ActivityScenarioRule<MainActivity>, MainActivity>

fun launchPostComposerFor(
    email: String,
    createNewPostRule: MainActivityRule,
    block: CreateNewPostRobot.() -> Unit
): CreateNewPostRobot {
    launchTimelineFor(email, "sOmEPas%123", createNewPostRule) {
        tapOnCreateNewPost()
    }
    return CreateNewPostRobot(createNewPostRule).apply(block)
}

class CreateNewPostRobot(private val rule: MainActivityRule) {
    fun typePost(postContent: String) {
        val postHint = rule.activity.getString(R.string.newPostHint)
        rule.onNodeWithText(postHint)
            .performTextInput(postContent)
    }

    fun submit() {
        val submitPost = rule.activity.getString(R.string.submitPost)
        rule.onNodeWithTag(submitPost)
            .performClick()
    }

    infix fun verify(
        block: CreateNewPostVerificationRobot.() -> Unit
    ): CreateNewPostVerificationRobot {
        return CreateNewPostVerificationRobot(rule).apply(block)
    }
}

class CreateNewPostVerificationRobot(private val rule: MainActivityRule) {

    fun newlyCreatedPostIsShown(userId: String, dateTime: String, postContent: String) {
        rule.onNodeWithText(userId).assertIsDisplayed()
        rule.onNodeWithText(dateTime).assertIsDisplayed()
        rule.onNodeWithText(postContent).assertIsDisplayed()
    }
}
