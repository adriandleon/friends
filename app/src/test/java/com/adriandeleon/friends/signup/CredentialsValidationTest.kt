package com.adriandeleon.friends.signup

import com.adriandeleon.friends.InstantTaskExecutor
import com.adriandeleon.friends.signup.state.SignUpState
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

@ExtendWith(InstantTaskExecutor::class)
class CredentialsValidationTest {

    @ParameterizedTest
    @CsvSource(
        "'email'",
        "'a@b.c'",
        "'ab@b.c'",
        "'ab@bc.c'",
        "''",
        "'      '",
    )
    fun invalidEmail(email: String) {
        val viewModel = SignUpViewModel()

        viewModel.createAccount(email, ":password:", ":irrelevant:")

        assertEquals(SignUpState.BadEmail, viewModel.signUpState.value)
    }
}