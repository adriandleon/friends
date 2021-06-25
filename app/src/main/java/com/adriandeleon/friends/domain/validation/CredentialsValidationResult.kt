package com.adriandeleon.friends.domain.validation

sealed class CredentialsValidationResult {
    object InvalidEmail : CredentialsValidationResult()
    object InvalidPassword : CredentialsValidationResult()
    object Valid : CredentialsValidationResult()
}