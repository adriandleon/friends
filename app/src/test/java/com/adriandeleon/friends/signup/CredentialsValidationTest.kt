package com.adriandeleon.friends.signup

import com.adriandeleon.friends.InstantTaskExecutor
import com.adriandeleon.friends.app.TestDispatcher
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
        "'   '",
        "'email.address'",
        "'email@address'",
    )
    fun `invalid email`(email: String) {
        val viewModel = SignUpViewModel(
            RegexCredentialsValidator(),
            UserRepository(InMemoryUserCatalog()),
            TestDispatcher()
        )

        viewModel.createAccount(email, ":password:", ":about:")

        assertEquals(SignUpState.BadEmail, viewModel.signUpState.value)
    }

    @ParameterizedTest
    @CsvSource(
        "''",
        "'     '",
        "'s",
        "'2A'",
        "'aB%'",
        "'12345678'",
        "'abcdefgh'",
        "'ABCDEFGH'",
        "'&*#^%*#%'",
        "'abcd5678'",
        "'ABCD5678'",
        "'&%#&5678'",
        "'abcdEFGH'",
        "'abcd%%#!'",
        "'ABCD*&^%'",
        "'123abcDEF'",
        "'123abc*&^'",
        "'123ABC*&^'",
        "'abcABC*&^'",
    )
    fun `invalid password`(password: String) {
        val viewModel = SignUpViewModel(
            RegexCredentialsValidator(),
            UserRepository(InMemoryUserCatalog()),
            TestDispatcher()
        )

        viewModel.createAccount("anna@friends.com", password, ":about:")

        assertEquals(SignUpState.BadPassword, viewModel.signUpState.value)
    }

    @Test
    fun `valid credentials`() {
        val validator = RegexCredentialsValidator()
        val result = validator.validate("john@friends.com", "123ABcd^%$")
        assertEquals(CredentialsValidationResult.Valid, result)
    }
}