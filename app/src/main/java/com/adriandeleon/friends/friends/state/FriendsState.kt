package com.adriandeleon.friends.friends.state

import com.adriandeleon.friends.domain.user.Friend

sealed class FriendsState {
    data object Loading : FriendsState()
    data class Loaded(val friends: List<Friend>) : FriendsState()
    data object BackendError : FriendsState()
    data object Offline : FriendsState()
}