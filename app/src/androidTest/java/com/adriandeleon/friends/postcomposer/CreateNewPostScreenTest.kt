package com.adriandeleon.friends.postcomposer

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.adriandeleon.friends.MainActivity
import com.adriandeleon.friends.domain.post.DelayingPostsCatalog
import com.adriandeleon.friends.domain.post.InMemoryPostCatalog
import com.adriandeleon.friends.domain.post.OfflinePostCatalog
import com.adriandeleon.friends.domain.post.PostCatalog
import com.adriandeleon.friends.domain.post.UnavailablePostCatalog
import com.adriandeleon.friends.domain.user.InMemoryUserDataStore
import com.adriandeleon.friends.infraestructure.ControllableClock
import org.junit.After
import org.junit.Ignore
import org.junit.Rule
import org.junit.Test
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module
import java.util.Calendar

class CreateNewPostScreenTest {

    @get:Rule
    val createNewPostRule = createAndroidComposeRule<MainActivity>()

    private val timestamp = Calendar.getInstance()
        .also { it.set(2021, 9, 30, 15, 30) }
        .timeInMillis

    @Test
    fun createNewPost() {
        replacePostCatalogWith(InMemoryPostCatalog(clock = ControllableClock(timestamp)))

        launchPostComposerFor("user@friends.com", createNewPostRule) {
            typePost("My New Post")
            submit()
        } verify {
            newlyCreatedPostIsShown("userId", "30-10-2021 15:30", "My New Post")
        }
    }

    @Ignore("This test is broken")
    @Test
    fun createMultiplePosts() {
        replacePostCatalogWith(InMemoryPostCatalog(clock = ControllableClock(timestamp)))

        launchPostComposerFor("user@friends.com", createNewPostRule) {
            typePost("My first post")
            submit()
            tapOnCreateNewPost()
            typePost("My Second Post")
            submit()
        } verify {
            newlyCreatedPostIsShown("userId", "30-10-2021 15:30", "My first post")
            newlyCreatedPostIsShown("userId", "30-10-2021 15:30", "My Second Post")
        }
    }

    @Test
    fun showsBlockingLoading() {
        replacePostCatalogWith(DelayingPostsCatalog())

        launchPostComposerFor("bob@friends.com", createNewPostRule) {
            typePost("Waiting")
            submit()
        } verify {
            blockingLoadingIsShown()
        }
    }

    @Test
    fun showsBackendError() {
        replacePostCatalogWith(UnavailablePostCatalog())

        launchPostComposerFor("dan@friends.com", createNewPostRule) {
            typePost("Some Post")
            submit()
        } verify {
            backendErrorIsShown()
        }
    }

    @Test
    fun showsOfflineError() {
        replacePostCatalogWith(OfflinePostCatalog())

        launchPostComposerFor("mia@friends.com", createNewPostRule) {
            typePost("My New Post")
            submit()
        } verify {
            offlineErrorIsShown()
        }
    }

    @After
    fun tearDown() {
        replacePostCatalogWith(InMemoryPostCatalog())
        replaceUserDataWith(InMemoryUserDataStore())
    }

    private fun replacePostCatalogWith(postCatalog: PostCatalog) {
        val module = module {
            single { postCatalog }
        }
        loadKoinModules(module)
    }

    private fun replaceUserDataWith(userData: InMemoryUserDataStore) {
        val module = module {
            single { userData }
        }
        loadKoinModules(module)
    }
}

