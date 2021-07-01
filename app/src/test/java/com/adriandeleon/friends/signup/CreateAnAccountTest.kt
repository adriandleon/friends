package com.adriandeleon.friends.signup

import com.adriandeleon.friends.InstantTaskExecutor
import com.adriandeleon.friends.domain.user.User
import com.adriandeleon.friends.domain.validation.RegexCredentialsValidator
import com.adriandeleon.friends.signup.state.SignUpState
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(InstantTaskExecutor::class)
class CreateAnAccountTest {

    @Test
    fun `account created`() {
        val maya = User("mayaId", "maya@friends.com", "About Maya")
        val viewModel = SignUpViewModel(RegexCredentialsValidator())

        viewModel.createAccount(maya.email, "MaY@2021", maya.about)

        assertEquals(SignUpState.SignUp(maya), viewModel.signUpState.value)
    }

    @Test
    fun `another account created`() {
        val bob = User("bobId", "bob@friends.com", "about Bob")
        val viewModel = SignUpViewModel(RegexCredentialsValidator())

        viewModel.createAccount(bob.email, "Ple@seSubscribe1", bob.about)

        assertEquals(SignUpState.SignUp(bob), viewModel.signUpState.value)
    }

    @Test
    fun `create duplicate account`() {
        val anna = User("annaId", "anna@friends.com", "about Anna")
        val password = "AnNaPas$123"
        val viewModel = SignUpViewModel(RegexCredentialsValidator()).also {
            it.createAccount(anna.email, password, anna.about)
        }

        viewModel.createAccount(anna.email, password, anna.about)

        assertEquals(SignUpState.DuplicateAccount, viewModel.signUpState.value)
    }
}