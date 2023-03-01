package com.adriandeleon.friends.timeline

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.adriandeleon.friends.MainActivity
import com.adriandeleon.friends.R
import com.adriandeleon.friends.domain.post.Post
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
    private val rule: MainActivityRule
) {
    fun tapOnCreateNewPost() {
        val createNewPost = rule.activity.getString(R.string.createNewPost)
        rule.onNodeWithTag(createNewPost)
            .performClick()
    }

    infix fun verify(block: TimelineVerificationRobot.() -> Unit): TimelineVerificationRobot {
        return TimelineVerificationRobot(rule).apply(block)
    }
}

class TimelineVerificationRobot(private val rule: MainActivityRule) {

    fun emptyTimelineMessageIsDisplayed() {
        val emptyTimelineMessage = rule.activity.getString(R.string.emptyTimelineMessage)
        rule.onNodeWithText(emptyTimelineMessage)
            .assertIsDisplayed()
    }

    fun postsAreDisplayed(vararg posts: Post) {
        posts.forEach { post ->
            rule.onNodeWithText(post.postText)
                .assertIsDisplayed()
        }
    }

    fun newPostComposerIsDisplayed() {
        val createNewPost = rule.activity.getString(R.string.createNewPost)
        rule.onNodeWithText(createNewPost)
            .assertIsDisplayed()
    }
}
