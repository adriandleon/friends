package com.adriandeleon.friends.signup

import com.adriandeleon.friends.InstantTaskExecutor
import com.adriandeleon.friends.domain.user.InMemoryUserCatalog
import com.adriandeleon.friends.domain.user.User
import com.adriandeleon.friends.domain.user.UserRepository
import com.adriandeleon.friends.domain.validation.RegexCredentialsValidator
import com.adriandeleon.friends.signup.state.SignUpState
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(InstantTaskExecutor::class)
class CreateAnAccountTest {

    @Test
    fun accountCreated() {
        val maya = User("mayaId", "maya@friends.com", "about Maya")
        val viewModel = SignUpViewModel(
            RegexCredentialsValidator(),
            UserRepository(InMemoryUserCatalog())
        )

        viewModel.createAccount(maya.email, "MaY@2022", maya.about)

        assertEquals(SignUpState.SignedUp(maya), viewModel.signUpState.value)
    }

    @Test
    fun anotherAccountCreated() {
        val bob = User("bobId", "bob@friends.com", "about Bob")
        val viewModel = SignUpViewModel(
            RegexCredentialsValidator(),
            UserRepository(InMemoryUserCatalog())
        )

        viewModel.createAccount(bob.email, "Ple@seSubscribe1", bob.about)

        assertEquals(SignUpState.SignedUp(bob), viewModel.signUpState.value)
    }

    @Test
    fun createDuplicateAccounts() {
        val anna = User("annaId", "anna@friends.com", "about Anna")
        val password = "AnNaPas$123"
        val viewModel = SignUpViewModel(
            RegexCredentialsValidator(),
            UserRepository(InMemoryUserCatalog())
        ).also {
            it.createAccount(anna.email, password, anna.about)
        }

        viewModel.createAccount(anna.email, password, anna.about)

        assertEquals(SignUpState.DuplicateAccount, viewModel.signUpState.value)
    }
}