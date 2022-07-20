package com.adriandeleon.friends.signup

import com.adriandeleon.friends.domain.exceptions.BackendException
import com.adriandeleon.friends.domain.user.User
import com.adriandeleon.friends.domain.user.UserCatalog
import com.adriandeleon.friends.domain.user.UserRepository
import com.adriandeleon.friends.signup.state.SignUpState
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class FailedAccountCreationTest {

    @Test
    fun `backend error`() {
        val userRepository = UserRepository(UnavailableUserCatalog())
        val result = userRepository.signUp("email", "password", "about")

        assertEquals(SignUpState.BackendError, result)
    }

    class UnavailableUserCatalog : UserCatalog {
        override fun createUser(email: String, password: String, about: String): User {
            throw BackendException()
        }
    }
}