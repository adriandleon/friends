package com.adriandeleon.friends.postcomposer

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.adriandeleon.friends.MainActivity
import com.adriandeleon.friends.domain.post.InMemoryPostCatalog
import com.adriandeleon.friends.domain.post.PostCatalog
import com.adriandeleon.friends.domain.user.InMemoryUserData
import com.adriandeleon.friends.infraestructure.ControllableClock
import org.junit.After
import org.junit.Rule
import org.junit.Test
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module
import java.util.Calendar

class CreateNewPostScreenTest {

    @get:Rule
    val createNewPostRule = createAndroidComposeRule<MainActivity>()

    private val timestampWithTimezoneOffset = Calendar.getInstance()
        .also { it.set(2023, 11, 27, 18, 30) }
        .timeInMillis

    @Test
    fun createNewPost() {
        replaceUserDataWith(InMemoryUserData("userId"))
        replacePostCatalogWith(InMemoryPostCatalog(clock = ControllableClock(timestampWithTimezoneOffset)))

        launchPostComposerFor("user@friends.com", createNewPostRule) {
            typePost("My New Post")
            submit()
        } verify {
            newlyCreatedPostIsShown("userId", "27-11-2023 15:30", "My New Post")
        }
    }

    @Test
    fun createMultiplePosts() {
        replaceUserDataWith(InMemoryUserData("userId"))
        replacePostCatalogWith(InMemoryPostCatalog(clock = ControllableClock(timestampWithTimezoneOffset)))

        launchPostComposerFor("user@friends.com", createNewPostRule) {
            typePost("My first post")
            submit()
            tapOnCreateNewPost()
            typePost("My second post")
            submit()
        } verify {
            newlyCreatedPostIsShown("userId", "27-11-2023 15:30", "my first post")
        }
    }

    @After
    fun tearDown() {
        replacePostCatalogWith(InMemoryPostCatalog())
        replaceUserDataWith(InMemoryUserData(""))
    }

    private fun replacePostCatalogWith(postCatalog: PostCatalog) {
        val module = module {
            single { postCatalog }
        }
        loadKoinModules(module)
    }

    private fun replaceUserDataWith(userData: InMemoryUserData) {
        val module = module {
            single { userData }
        }
        loadKoinModules(module)
    }
}

