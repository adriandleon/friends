package com.adriandeleon.friends.domain.user

class InMemoryUserData(
    private val loggedInUserId: String
) {
    fun loggedInUserId() = loggedInUserId
}