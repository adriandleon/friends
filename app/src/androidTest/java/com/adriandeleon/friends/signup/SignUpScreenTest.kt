package com.adriandeleon.friends.signup

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.adriandeleon.friends.MainActivity
import com.adriandeleon.friends.domain.user.InMemoryUserCatalog
import com.adriandeleon.friends.domain.user.UserCatalog
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module

class SignUpScreenTest {

    @get:Rule
    val signUpTestRule = createAndroidComposeRule<MainActivity>()

    private val userCatalog = InMemoryUserCatalog()

    private val signUpModule = module {
        factory<UserCatalog>(override = true) { userCatalog }
    }

    @Before
    fun setUp() {
        loadKoinModules(signUpModule)
    }

    @Test
    fun performSignUp() {
        launchSignUpScreen(signUpTestRule) {
            typeEmail("email@friends.app")
            typePassword("PassW0rd!")
            submit()
        } verify {
            timelineScreenIsPresent()
        }
    }

    @Test
    fun displayDuplicateAccountError() {
        launchSignUpScreen(signUpTestRule) {
            val signedUpUserEmail = "alice@friends.com"
            val signedUpUserPassword = "@l1cePass"
            createUserWith(signedUpUserEmail, signedUpUserPassword)

            typeEmail(signedUpUserEmail)
            typePassword(signedUpUserPassword)
            submit()
        } verify {
            duplicateAccountErrorIsShown()
        }
    }


    @After
    fun tearDown() {
        val resetModule = module {
            single(override = true) { InMemoryUserCatalog() }
        }
        loadKoinModules(resetModule)
    }

    private fun createUserWith(
        signedUpUserEmail: String,
        signedUpUserPassword: String
    ) {
        userCatalog.createUser(signedUpUserEmail, signedUpUserPassword, "")
    }
}