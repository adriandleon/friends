package com.adriandeleon.friends.signup

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.adriandeleon.friends.MainActivity
import com.adriandeleon.friends.domain.user.InMemoryUserCatalog
import com.adriandeleon.friends.domain.user.OfflineUserCatalog
import com.adriandeleon.friends.domain.user.UnavailableUserCatalog
import com.adriandeleon.friends.domain.user.User
import com.adriandeleon.friends.domain.user.UserCatalog
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module

class SignedUpScreenTest {

    @get:Rule
    val signUpTestRule = createAndroidComposeRule<MainActivity>()

    private val signUpModule = module {
        factory<UserCatalog> { InMemoryUserCatalog() }
    }

    @Before
    fun setUp() {
        loadKoinModules(signUpModule)
    }

    @Test
    fun performSignUp() {
        launchSignUpScreen(signUpTestRule) {
            typeEmail("someone@friends.com")
            typePassword("PassW0rd!")
            submit()
        } verify {
            timelineScreenIsPresent()
        }
    }

    @Test
    fun displayBadEmailError() {
        launchSignUpScreen(signUpTestRule) {
            typeEmail("bad.email")
            submit()
        } verify {
            badEmailErrorIsShown()
        }
    }

    @Test
    fun resetBadEmailError() {
        launchSignUpScreen(signUpTestRule) {
            typeEmail("email")
            submit()
            typeEmail("email@")
        } verify {
            badEmailErrorIsNotShown()
        }
    }

    @Test
    fun displayBadPassword() {
        launchSignUpScreen(signUpTestRule) {
            typeEmail("james@friends.com")
            typePassword("abc")
            submit()
        } verify {
            badPasswordErrorIsShown()
        }
    }

    @Test
    fun resetBadPasswordError() {
        launchSignUpScreen(signUpTestRule) {
            typeEmail("valid@email.com")
            typePassword("ads")
            submit()
            typePassword("newTry")
        } verify {
            badPasswordErrorIsNowShown()
        }
    }

    @Test
    fun displayDuplicateAccountError() = runBlocking<Unit> {
        val signedUpUserEmail = "alice@friends.com"
        val signedUpUserPassword = "@l1cePass"
        replaceUserCatalogWith(InMemoryUserCatalog().apply {
            createUser(signedUpUserEmail, signedUpUserPassword, "")
        })

        launchSignUpScreen(signUpTestRule) {
            typeEmail(signedUpUserEmail)
            typePassword(signedUpUserPassword)
            submit()
        } verify {
            duplicateAccountErrorIsShown()
        }
    }

    @Test
    fun displayBackendError() {
        replaceUserCatalogWith(UnavailableUserCatalog())

        launchSignUpScreen(signUpTestRule) {
            typeEmail("joe@friends.com")
            typePassword("Jo3PassWord#@")
            submit()
        } verify {
            backendErrorIsShown()
        }
    }

    @Test
    fun displayOfflineError() {
        replaceUserCatalogWith(OfflineUserCatalog())

        launchSignUpScreen(signUpTestRule) {
            typeEmail("joe@friends.com")
            typePassword("Jo3PassWord#@")
            submit()
        } verify {
            offlineErrorIsShown()
        }
    }

    @Test
    fun displayBlockingLoading() {
        replaceUserCatalogWith(DelayingUserCatalog())
        launchSignUpScreen(signUpTestRule) {
            typeEmail("caly@friends.com")
            typePassword("C@lyP1ss#")
            submit()
        } verify {
            blockingLoadingIsShown()
        }
    }

    class DelayingUserCatalog : UserCatalog {
        override suspend fun createUser(email: String, password: String, about: String): User {
            delay(100)
            return User("someId", email, about)
        }

        override fun followedBy(userId: String): List<String> {
            return emptyList()
        }
    }

    @After
    fun tearDown() {
        replaceUserCatalogWith(InMemoryUserCatalog())
    }

    private fun replaceUserCatalogWith(userCatalog: UserCatalog) {
        val replaceModule = module {
            factory { userCatalog }
        }
        loadKoinModules(replaceModule)
    }
}