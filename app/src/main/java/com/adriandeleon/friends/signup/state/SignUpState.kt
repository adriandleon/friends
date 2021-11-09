package com.adriandeleon.friends.signup.state

import com.adriandeleon.friends.domain.user.User

sealed class SignUpState {
    object Loading : SignUpState()
    data class SignedUp(val user: User) : SignUpState()
    object BadEmail : SignUpState()
    object BadPassword : SignUpState()
    object DuplicateAccount : SignUpState()
    object BackendError : SignUpState()
    object Offline : SignUpState()
}
