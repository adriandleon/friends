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
}