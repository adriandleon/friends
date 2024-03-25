package com.adriandeleon.friends.domain.people

import com.adriandeleon.friends.domain.user.Friend

interface PeopleCatalog {
    fun loadPeopleFor(userId: String): List<Friend>
}