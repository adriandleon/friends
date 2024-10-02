package com.adriandeleon.friends.domain.people

import com.adriandeleon.friends.domain.exceptions.BackendException
import com.adriandeleon.friends.domain.exceptions.ConnectionUnavailableException
import com.adriandeleon.friends.domain.user.UserCatalog
import com.adriandeleon.friends.people.state.PeopleState

class PeopleRepository(
    private val peopleCatalog: PeopleCatalog,
    private val userCatalog: UserCatalog
) {
    suspend fun loadPeopleFor(userId: String): PeopleState {
        return try {
            val peopleForUser = userCatalog.loadPeopleFor(userId)
            PeopleState.Loaded(peopleForUser)
        } catch (backendException: BackendException) {
            PeopleState.BackendError
        } catch (offlineException: ConnectionUnavailableException) {
            PeopleState.Offline
        }
    }
}