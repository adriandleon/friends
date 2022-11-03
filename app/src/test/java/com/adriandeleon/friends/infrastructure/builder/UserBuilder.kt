package com.adriandeleon.friends.infrastructure.builder

import com.adriandeleon.friends.domain.user.User
import java.util.*

class UserBuilder {
    private var userId = UUID.randomUUID().toString()
    private var userEmail = "user@friends.com"
    private var aboutUser = "About User"

    companion object {
        fun aUser(): UserBuilder = UserBuilder()
    }

    fun withId(id: String): UserBuilder = this.apply {
        userId = id
    }

    fun build(): User {
        return User(userId, userEmail, aboutUser)
    }
}