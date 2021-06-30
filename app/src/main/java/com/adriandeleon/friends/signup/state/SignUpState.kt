package com.adriandeleon.friends.signup.state

import com.adriandeleon.friends.domain.user.User

sealed class SignUpState {
    data class SignUp(val user: User) : SignUpState()
    object BadEmail : SignUpState()
    object BadPassword : SignUpState()
}
