package com.adriandeleon.friends.domain.people

import com.adriandeleon.friends.domain.exceptions.BackendException
import com.adriandeleon.friends.domain.exceptions.ConnectionUnavailableException
import com.adriandeleon.friends.domain.user.Friend

class InMemoryPeopleCatalog(
    private val peopleForUserId: Map<String, List<Friend>>
) : PeopleCatalog {

    override suspend fun loadPeopleFor(userId: String): List<Friend> {
        if (userId.isBlank()) throw ConnectionUnavailableException()
        return peopleForUserId[userId] ?: throw BackendException()
    }
}