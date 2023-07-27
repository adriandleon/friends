package com.adriandeleon.friends.signup

import com.adriandeleon.friends.domain.user.OfflineUserCatalog
import com.adriandeleon.friends.domain.user.UnavailableUserCatalog
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
}