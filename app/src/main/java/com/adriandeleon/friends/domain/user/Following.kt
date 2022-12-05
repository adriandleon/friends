package com.adriandeleon.friends.domain.user

data class Following(
    val userId: String,
    val followedId: String
)