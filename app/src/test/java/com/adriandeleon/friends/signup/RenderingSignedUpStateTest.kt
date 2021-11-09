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
class RenderingSignedUpStateTest {

    @Test
    fun uiStatesAreDeliveredInParticularOrder() {
        val userRepository = UserRepository(InMemoryUserCatalog())
        val viewModel = SignUpViewModel(RegexCredentialsValidator(), userRepository)
        val tom = User("tomId", "tom@friends.com", "about Tom")
        val deliveredStates = mutableListOf<SignUpState>()
        viewModel.signUpState.observeForever { deliveredStates.add(it) }

        viewModel.createAccount(tom.email, "P@ssWord1#$", tom.about)

        assertEquals(
            listOf(SignUpState.Loading, SignUpState.SignedUp(tom)),
            deliveredStates
        )
    }
}