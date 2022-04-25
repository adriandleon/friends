package com.adriandeleon.friends.signup

import com.adriandeleon.friends.InstantTaskExecutor
import com.adriandeleon.friends.signup.state.SignUpState
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(InstantTaskExecutor::class)
class CredentialsValidationTest {

    @Test
    fun invalidEmail() {
        val viewModel = SignUpViewModel()

        viewModel.createAccount("email", ":password:", ":irrelevant:")

        assertEquals(SignUpState.BadEmail, viewModel.signUpState.value)
    }
}