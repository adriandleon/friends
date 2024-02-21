package com.adriandeleon.friends.domain.user

class InMemoryUserDataStore(
    private val loggedInUserId: String = ""
) {
    fun loggedInUserId() = loggedInUserId
}