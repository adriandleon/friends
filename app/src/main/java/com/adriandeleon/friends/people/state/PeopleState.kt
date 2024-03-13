package com.adriandeleon.friends.people.state

import com.adriandeleon.friends.domain.user.Friend

sealed class PeopleState {
    data class Loaded(val friends: List<Friend>) : PeopleState()

    data object BackendError : PeopleState()
}