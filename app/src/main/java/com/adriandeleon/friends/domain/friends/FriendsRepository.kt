package com.adriandeleon.friends.domain.friends

import com.adriandeleon.friends.domain.exceptions.BackendException
import com.adriandeleon.friends.domain.exceptions.ConnectionUnavailableException
import com.adriandeleon.friends.domain.user.UserCatalog
import com.adriandeleon.friends.friends.state.FriendsState

class FriendsRepository(
    private val userCatalog: UserCatalog
) {
    suspend fun loadFriendsFor(userId: String): FriendsState {
        return try {
            val friendsForUser = userCatalog.loadFriendsFor(userId)
            FriendsState.Loaded(friendsForUser)
        } catch (backendException: BackendException) {
            FriendsState.BackendError
        } catch (offlineException: ConnectionUnavailableException) {
            FriendsState.Offline
        }
    }
}