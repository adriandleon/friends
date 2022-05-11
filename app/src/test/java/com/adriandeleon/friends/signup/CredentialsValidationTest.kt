package com.adriandeleon.friends.signup

import com.adriandeleon.friends.InstantTaskExecutor
import com.adriandeleon.friends.domain.user.InMemoryUserCatalog
import com.adriandeleon.friends.domain.user.UserRepository
import com.adriandeleon.friends.domain.validation.CredentialsValidationResult
import com.adriandeleon.friends.domain.validation.RegexCredentialsValidator
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
        val viewModel = SignUpViewModel(
            RegexCredentialsValidator(),
            UserRepository(InMemoryUserCatalog())
        )

        viewModel.createAccount(email, ":password:", ":irrelevant:")

        assertEquals(SignUpState.BadEmail, viewModel.signUpState.value)
    }

    @ParameterizedTest
    @CsvSource(
        "''",
        "'           '",
        "'12345678'",
        "'abcd5678'",
        "'abcDEF78'",
        "'abcdef78#$'",
        "'ABCDEF78#$'",
    )
    fun invalidPassword(password: String) {
        val viewModel = SignUpViewModel(
            RegexCredentialsValidator(),
            UserRepository(InMemoryUserCatalog())
        )

        viewModel.createAccount("anna@friends.com", password, ":about:")

        assertEquals(SignUpState.BadPassword, viewModel.signUpState.value)
    }

    @Test
    fun validCredentials() {
        val validator = RegexCredentialsValidator()
        val result = validator.validate("john@friends.com", "12ABcd3!^")
        assertEquals(CredentialsValidationResult.Valid, result)
    }
}