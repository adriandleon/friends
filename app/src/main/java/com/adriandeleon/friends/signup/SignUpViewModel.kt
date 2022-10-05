package com.adriandeleon.friends.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adriandeleon.friends.domain.user.UserRepository
import com.adriandeleon.friends.domain.validation.CredentialsValidationResult
import com.adriandeleon.friends.domain.validation.RegexCredentialsValidator
import com.adriandeleon.friends.signup.state.SignUpState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SignUpViewModel(
    private val credentialsValidator: RegexCredentialsValidator,
    private val userRepository: UserRepository
) : ViewModel() {

    private val mutableSignUpState = MutableLiveData<SignUpState>()
    val signUpState: LiveData<SignUpState> = mutableSignUpState

    fun createAccount(
        email: String,
        password: String,
        about: String,
    ) {
        when (credentialsValidator.validate(email, password)) {
            is CredentialsValidationResult.InvalidEmail ->
                mutableSignUpState.value = SignUpState.BadEmail
            is CredentialsValidationResult.InvalidPassword ->
                mutableSignUpState.value = SignUpState.BadPassword
            is CredentialsValidationResult.Valid -> proceedWithSignUp(email, password, about)
        }
    }

    private fun proceedWithSignUp(email: String, password: String, about: String) {
        viewModelScope.launch {
            mutableSignUpState.value = SignUpState.Loading
            mutableSignUpState.value = withContext(Dispatchers.Unconfined) {
                userRepository.signUp(email, password, about)
            }
        }
    }
}
