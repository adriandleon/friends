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
    fun accountCreated() {
        val viewModel = SignUpViewModel(RegexCredentialsValidator())

        viewModel.createAccount("maya@friends.com", "MaY@2022", "about Maya")

        val maya = User("mayaId", "maya@friends.com", "about Maya")
        assertEquals(SignUpState.SignedUp(maya), viewModel.signUpState.value)
    }
}