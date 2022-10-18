package com.adriandeleon.friends.signup

import com.adriandeleon.friends.domain.exceptions.BackendException
import com.adriandeleon.friends.domain.exceptions.ConnectionUnavailableException
import com.adriandeleon.friends.domain.user.User
import com.adriandeleon.friends.domain.user.UserCatalog
import com.adriandeleon.friends.domain.user.UserRepository
import com.adriandeleon.friends.signup.state.SignUpState
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class FailedAccountCreationTest {

    @Test
    fun `backend error`() = runBlocking {
        val userRepository = UserRepository(UnavailableUserCatalog())

        val result = userRepository.signUp(":email:", ":password:", ":about:")

        assertEquals(SignUpState.BackendError, result)
    }

    @Test
    fun `offline error`() = runBlocking {
        val userRepository = UserRepository(OfflineUserCatalog())
        val result = userRepository.signUp(":email:", ":password:", ":about:")
        assertEquals(SignUpState.Offline, result)
    }

    class OfflineUserCatalog : UserCatalog {

        override suspend fun createUser(email: String, password: String, about: String): User {
            throw ConnectionUnavailableException()
        }
    }

    class UnavailableUserCatalog : UserCatalog {

        override suspend fun createUser(email: String, password: String, about: String): User {
            throw BackendException()
        }
    }
}