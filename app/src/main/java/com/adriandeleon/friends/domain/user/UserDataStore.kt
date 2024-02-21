package com.adriandeleon.friends.domain.user

interface UserDataStore {
    fun loggedInUserId(): String
    fun storeLoggedInUserId(userId: String)
}