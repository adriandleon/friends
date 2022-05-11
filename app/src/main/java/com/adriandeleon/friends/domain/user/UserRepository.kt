package com.adriandeleon.friends.domain.user

import com.adriandeleon.friends.domain.exceptions.DuplicateAccountException
import com.adriandeleon.friends.signup.state.SignUpState

class UserRepository(
    private val userCatalog: InMemoryUserCatalog
) {

    fun signUp(
        email: String,
        password: String,
        about: String
    ): SignUpState {
        return try {
            val user = userCatalog.createUser(email, password, about)
            SignUpState.SignedUp(user)
        } catch (exception: DuplicateAccountException) {
            SignUpState.DuplicateAccount
        }
    }
}