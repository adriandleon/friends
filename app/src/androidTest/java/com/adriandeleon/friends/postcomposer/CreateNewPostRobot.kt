package com.adriandeleon.friends.postcomposer

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onLast
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

    fun tapOnCreateNewPost() {
        val createNewPost = rule.activity.getString(R.string.createNewPost)
        rule.onNodeWithTag(createNewPost)
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
        rule.onAllNodesWithText(userId).onFirst().assertIsDisplayed()
        rule.onAllNodesWithText(userId).onLast().assertIsDisplayed()
        rule.onAllNodesWithText(dateTime).onFirst().assertIsDisplayed()
        rule.onAllNodesWithText(dateTime).onLast().assertIsDisplayed()
        rule.onNodeWithText(postContent).assertIsDisplayed()
    }

    fun backendErrorIsShown() {
        val errorMessage = rule.activity.getString(R.string.creatingPostError)
        rule.onNodeWithText(errorMessage)
            .assertIsDisplayed()
    }

    fun offlineErrorIsShown() {
        val offlineError = rule.activity.getString(R.string.offlineError)
        rule.onNodeWithText(offlineError)
            .assertIsDisplayed()
    }

    fun blockingLoadingIsShown() {
        val loading = rule.activity.getString(R.string.loading)
        rule.onNodeWithTag(loading)
            .assertIsDisplayed()
    }
}
