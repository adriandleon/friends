package com.adriandeleon.friends.domain.user

import com.adriandeleon.friends.domain.exceptions.DuplicateAccountException

class InMemoryUserCatalog(
    private val usersForPassword: MutableMap<String, MutableList<User>> = mutableMapOf(),
    private val followings: List<Following> = mutableListOf()
) : UserCatalog {

    override suspend fun createUser(
        email: String,
        password: String,
        about: String
    ): User {
        checkAccountExists(email)
        val userId = createUserIdFor(email) + "Id"
        val user = User(userId, email, about)
        saveUser(password, user)
        return user
    }

    fun followedBy(userId: String): List<String> {
        return followings.filter { it.userId == userId }
            .map { it.followedId }
    }

    private fun checkAccountExists(email: String) {
        if (usersForPassword.values.flatten().any { it.email == email }) {
            throw DuplicateAccountException()
        }
    }

    private fun createUserIdFor(email: String): String {
        return email.takeWhile { it != '@' }
    }

    private fun saveUser(password: String, user: User) {
        usersForPassword.getOrPut(password, ::mutableListOf).add(user)
    }
}