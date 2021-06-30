package com.adriandeleon.friends.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.adriandeleon.friends.domain.user.User
import com.adriandeleon.friends.domain.validation.CredentialsValidationResult
import com.adriandeleon.friends.domain.validation.RegexCredentialsValidator
import com.adriandeleon.friends.signup.state.SignUpState

class SignUpViewModel(private val credentialsValidator: RegexCredentialsValidator) {

    private val _mutableSignUpState = MutableLiveData<SignUpState>()
    val signUpState: LiveData<SignUpState> = _mutableSignUpState

    fun createAccount(email: String, password: String, about: String) {

        when (credentialsValidator.validate(email, password)) {
            is CredentialsValidationResult.InvalidEmail ->
                _mutableSignUpState.value = SignUpState.BadEmail
            is CredentialsValidationResult.InvalidPassword ->
                _mutableSignUpState.value = SignUpState.BadPassword
            is CredentialsValidationResult.Valid -> {
                val userId = email.takeWhile { it != '@' } + "Id"
                if (email.contains("bob")) {
                    val user = User(userId, email, about)
                    _mutableSignUpState.value = SignUpState.SignUp(user)
                } else {
                    val user = User(userId, email, about)
                    _mutableSignUpState.value = SignUpState.SignUp(user)
                }
            }
        }
    }
}