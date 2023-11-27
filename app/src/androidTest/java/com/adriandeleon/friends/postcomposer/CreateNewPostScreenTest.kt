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
import java.time.LocalDateTime
import java.time.ZoneOffset

class CreateNewPostScreenTest {

    @get:Rule
    val createNewPostRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun createNewPost() {
        val timestampWithTimezoneOffset = LocalDateTime
            .of(2023, 11, 27, 18, 30)
            .toInstant(ZoneOffset.ofTotalSeconds(0))
            .toEpochMilli()
        replaceUserDataWith(InMemoryUserData("userId"))
        replacePostCatalogWith(InMemoryPostCatalog(clock = ControllableClock(timestampWithTimezoneOffset)))
        launchPostComposerFor("user@friends.com", createNewPostRule) {
            typePost("My New Post")
            submit()
        } verify {
            newlyCreatedPostIsShown("userId", "27-11-2023 15:30", "My New Post")
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

