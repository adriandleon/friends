package com.adriandeleon.friends.timeline

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.adriandeleon.friends.MainActivity
import com.adriandeleon.friends.domain.post.DelayingPostsCatalog
import com.adriandeleon.friends.domain.post.InMemoryPostCatalog
import com.adriandeleon.friends.domain.post.OfflinePostCatalog
import com.adriandeleon.friends.domain.post.Post
import com.adriandeleon.friends.domain.post.PostCatalog
import com.adriandeleon.friends.domain.post.UnavailablePostCatalog
import org.junit.After
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
        replacePostCatalogWith(InMemoryPostCatalog(mutableListOf(post1, post2)))

        launchTimelineFor(email, password, timelineTestRule) {
            // no operation
        } verify {
            postsAreDisplayed(post1, post2)
        }
    }

    @Test
    fun opensPostComposer() {
        launchTimelineFor("test@email.com", "s0mEPa$$123", timelineTestRule) {
            tapOnCreateNewPost()
        } verify {
            newPostComposerIsDisplayed()
        }
    }

    @Test
    fun showsLoadingIndicator() {
        replacePostCatalogWith(DelayingPostsCatalog())
        launchTimelineFor("testLoading@email.com", "s0mEPa$$123", timelineTestRule) {
            // no operation
        } verify {
            loadingIndicatorIsDisplayed()
        }
    }

    @Test
    fun showsBackendError() {
        replacePostCatalogWith(UnavailablePostCatalog())
        launchTimelineFor("backend.error@friends.com", "s0mEPa$123", timelineTestRule) {
            // no operation
        } verify {
            backendErrorIsDisplayed()
        }
    }

    @Test
    fun showsOfflineError() {
        replacePostCatalogWith(OfflinePostCatalog())
        launchTimelineFor("offline.error@friends.com", "s0mEPa$123", timelineTestRule) {
            // no operation
        } verify {
            offlineErrorIsDisplayed()
        }
    }

    @After
    fun tearDown() {
        replacePostCatalogWith(InMemoryPostCatalog())
    }

    private fun replacePostCatalogWith(postsCatalog: PostCatalog) {
        val replaceModule = module {
            factory { postsCatalog }
        }
        loadKoinModules(replaceModule)
    }
}