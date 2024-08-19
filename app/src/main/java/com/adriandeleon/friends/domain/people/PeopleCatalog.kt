package com.adriandeleon.friends.domain.people

import com.adriandeleon.friends.domain.user.Friend

interface PeopleCatalog {
    suspend fun loadPeopleFor(userId: String): List<Friend>
}