package com.adriandeleon.friends.timeline

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.adriandeleon.friends.MainActivity
import com.adriandeleon.friends.domain.post.InMemoryPostCatalog
import com.adriandeleon.friends.domain.post.Post
import com.adriandeleon.friends.domain.post.PostCatalog
import org.junit.Rule
import org.junit.Test
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module

class TimelineScreenTest {

    @get:Rule
    val timelineTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun showsEmptyTimelineMessage() {
        val email = "lucy@friends.com"
        val password = "passPASS123#@"
        launchTimelineFor(email, password, timelineTestRule) {
            // no operation
        } verify {
            emptyTimelineMessageIsDisplayed()
        }
    }

    @Test
    fun showsAvailablePosts() {
        val email = "bob@friends.com"
        val password = "b0bPa$$#2023"
        val post1 = Post("post1", "bobId", "This is Bob's first post", 1L)
        val post2 = Post("post2", "bobId", "Bob's second post is here", 2L)

        val postsCatalog = InMemoryPostCatalog(listOf(post1, post2))
        val replaceModule = module {
            factory<PostCatalog>(override = true) { postsCatalog }
        }
        loadKoinModules(replaceModule)

        launchTimelineFor(email, password, timelineTestRule) {

        } verify {
            postsAreDisplayed(post1, post2)
        }
    }
}