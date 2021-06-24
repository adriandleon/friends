package com.adriandeleon.friends.signup

import com.adriandeleon.friends.InstantTaskExecutor
import com.adriandeleon.friends.signup.state.SignUpState
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(InstantTaskExecutor::class)
class CredentialsValidationTest {

    @Test
    fun `invalid email`() {
        val viewModel = SignUpViewModel()

        viewModel.createAccount("bademail", ":password:", ":about:")

        assertEquals(SignUpState.BadState, viewModel.signUpState.value)
    }
}