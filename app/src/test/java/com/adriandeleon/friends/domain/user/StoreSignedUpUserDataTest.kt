package com.adriandeleon.friends.domain.user

import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class StoreSignedUpUserDataTest {

    @Test
    fun `success signing up`() = runTest {
        val userDataStore = InMemoryUserDataStore()
        val userRepository = UserRepository(UserCatalogCreatingUserWith("userId"), userDataStore)

        userRepository.signUp(":email:", ":password:", ":about:")

        assertEquals("userId", userDataStore.loggedInUserId())
    }

    private class UserCatalogCreatingUserWith(
        private val desiredUserId: String
    ) : UserCatalog {

        override suspend fun createUser(email: String, password: String, about: String): User {
            return User(desiredUserId, email, about)
        }

        override fun followedBy(userId: String): List<String> {
            TODO("Not yet implemented")
        }
    }
}