package com.adriandeleon.friends.domain.user

import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class StoreSignedUpUserDataTest {

    @Test
    fun `success signing up`() = runTest {
        val userDataStore = InMemoryUserDataStore()
        val userRepository = UserRepository(InMemoryUserCatalog(), userDataStore)

        userRepository.signUp("user@email.com", ":password:", ":about:")

        assertEquals("userId", userDataStore.loggedInUserId())
    }
}